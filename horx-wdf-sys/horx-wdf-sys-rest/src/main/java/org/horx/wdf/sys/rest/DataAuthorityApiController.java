package org.horx.wdf.sys.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.converter.DictItemVoConverter;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.converter.DataAuthorityVoConverter;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityConfig;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityView;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.horx.wdf.sys.service.DataPermissionService;
import org.horx.wdf.sys.service.RoleService;
import org.horx.wdf.sys.service.UserService;
import org.horx.wdf.sys.vo.DataAuthorityVO;
import org.horx.wdf.sys.vo.DictItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据授权API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/dataAuthority")
public class DataAuthorityApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAuthorityApiController.class);

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private DataAuthorityConfig dataAuthorityConfig;

    @Autowired
    private DataPermissionService dataPermissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private DataAuthorityVoConverter dataAuthorityVoConverter;

    @Autowired
    private DictItemVoConverter dictItemVoConverter;


    /**
     * 默认权限新建。
     * @param dataAuthorityVO 数据授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.default")
    @PostMapping("default")
    public Result<Long> createForDefault(@ArgEntity(create = true) DataAuthorityVO dataAuthorityVO, @RequestParam String detailList) {
        DataAuthorityDTO dataAuthorityDTO = dataAuthorityVoConverter.fromVo(dataAuthorityVO);
        dataAuthorityDTO.setObjType(DataAuthorityObjTypeEnum.DEFAULT.getCode());
        dataAuthorityDTO.setObjId(0L);

        if (StringUtils.isNotEmpty(detailList)) {
            dataAuthorityDTO.setDetailList(convertDetail(detailList));
        }

        Long id = dataAuthorityService.create(dataAuthorityDTO);
        return new Result(id);
    }

    /**
     * 默认权限修改。
     * @param dataAuthorityVO 数据权限授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.default")
    @PutMapping("default/{id}")
    public Result modifyForDefault(@ArgEntity(modify = true) DataAuthorityVO dataAuthorityVO, @RequestParam String detailList) {
        DataAuthorityDTO dataAuthorityDTO = dataAuthorityVoConverter.fromVo(dataAuthorityVO);
        if (StringUtils.isNotEmpty(detailList)) {
            dataAuthorityDTO.setDetailList(convertDetail(detailList));
        }

        dataAuthorityService.modify(dataAuthorityDTO);
        return new Result();
    }

    /**
     * 角色权限新建。
     * @param dataAuthorityVO 数据授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.role")
    @PostMapping("role")
    public Result<Long> createForRole(@ArgEntity(create = true) DataAuthorityVO dataAuthorityVO, @RequestParam Long objId,
                                      @RequestParam String roleType, @RequestParam String detailList,
                                      @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        RoleDTO role = roleService.getByIdAuthorized(objId, sysDataAuth);
        if (role == null) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DataAuthorityDTO dataAuthorityDTO = dataAuthorityVoConverter.fromVo(dataAuthorityVO);
        if (StringUtils.isNotEmpty(detailList)) {
            dataAuthorityDTO.setDetailList(convertDetail(detailList));
        }

        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataAuthorityDTO.getDataPermissionId());
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        boolean valid = dataAuthorityView.checkDataAuthority(dataPermissionDef, dataAuthorityDTO, DataAuthorityObjTypeEnum.ROLE);
        if (!valid) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("sys.dataAuth.err.unauthorizedData"));
        }

        if (DataAuthorityObjTypeEnum.ADMIN_ROLE.getCode().equals(roleType)) {
            dataAuthorityDTO.setObjType(DataAuthorityObjTypeEnum.ADMIN_ROLE.getCode());
        } else {
            dataAuthorityDTO.setObjType(DataAuthorityObjTypeEnum.ROLE.getCode());
        }

        dataAuthorityDTO.setObjId(objId);
        Long id = dataAuthorityService.create(dataAuthorityDTO);
        return new Result(id);
    }

    /**
     * 角色权限修改。
     * @param dataAuthorityVO 数据权限授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.role")
    @PutMapping("role/{id}")
    public Result modifyForRole(@ArgEntity(modify = true) DataAuthorityVO dataAuthorityVO, @RequestParam String detailList, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        DataAuthorityDTO old = dataAuthorityService.getById(dataAuthorityVO.getId());
        if (!DataAuthorityObjTypeEnum.ROLE.getCode().equals(old.getObjType()) && !DataAuthorityObjTypeEnum.ADMIN_ROLE.getCode().equals(old.getObjType())) {
            return new Result(ErrorCodeEnum.A0400.getCode(), msgTool.getMsg("common.err.param"));
        }

        RoleDTO role = roleService.getByIdAuthorized(old.getObjId(), sysDataAuth);
        if (role == null) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DataAuthorityDTO dataAuthorityDTO = dataAuthorityVoConverter.fromVo(dataAuthorityVO);
        if (StringUtils.isNotEmpty(detailList)) {
            dataAuthorityDTO.setDetailList(convertDetail(detailList));
        }

        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(old.getDataPermissionId());
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        boolean valid = dataAuthorityView.checkDataAuthority(dataPermissionDef, dataAuthorityDTO, DataAuthorityObjTypeEnum.ROLE);
        if (!valid) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("sys.dataAuth.err.unauthorizedData"));
        }

        dataAuthorityService.modify(dataAuthorityDTO);
        return new Result();
    }

    /**
     * 用户权限新建。
     * @param dataAuthorityVO 数据授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.user")
    @PostMapping("user")
    public Result<Long> createForUser(@ArgEntity(create = true) DataAuthorityVO dataAuthorityVO, @RequestParam Long objId,
                                      @RequestParam String detailList, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        UserDTO user = userService.getByIdAuthorized(objId, sysDataAuth);
        if (user == null) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DataAuthorityDTO dataAuthorityDTO = dataAuthorityVoConverter.fromVo(dataAuthorityVO);
        if (StringUtils.isNotEmpty(detailList)) {
            dataAuthorityDTO.setDetailList(convertDetail(detailList));
        }

        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataAuthorityDTO.getDataPermissionId());
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        boolean valid = dataAuthorityView.checkDataAuthority(dataPermissionDef, dataAuthorityDTO, DataAuthorityObjTypeEnum.USER);
        if (!valid) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("sys.dataAuth.err.unauthorizedData"));
        }

        dataAuthorityDTO.setObjType(DataAuthorityObjTypeEnum.USER.getCode());
        dataAuthorityDTO.setObjId(objId);
        Long id = dataAuthorityService.create(dataAuthorityDTO);
        return new Result(id);
    }

    /**
     * 用户权限修改。
     * @param dataAuthorityVO 数据权限授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.user")
    @PutMapping("user/{id}")
    public Result modifyForUser(@ArgEntity(modify = true) DataAuthorityVO dataAuthorityVO, @RequestParam String detailList, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        DataAuthorityDTO old = dataAuthorityService.getById(dataAuthorityVO.getId());

        if (!DataAuthorityObjTypeEnum.USER.getCode().equals(old.getObjType())) {
            return new Result(ErrorCodeEnum.A0400.getCode(), msgTool.getMsg("common.err.param"));
        }

        UserDTO user = userService.getByIdAuthorized(old.getObjId(), sysDataAuth);
        if (user == null) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DataAuthorityDTO dataAuthorityDTO = dataAuthorityVoConverter.fromVo(dataAuthorityVO);
        if (StringUtils.isNotEmpty(detailList)) {
            dataAuthorityDTO.setDetailList(convertDetail(detailList));
        }

        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(old.getDataPermissionId());
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        boolean valid = dataAuthorityView.checkDataAuthority(dataPermissionDef, dataAuthorityDTO, DataAuthorityObjTypeEnum.USER);
        if (!valid) {
            return new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("sys.dataAuth.err.unauthorizedData"));
        }

        dataAuthorityService.modify(dataAuthorityDTO);
        return new Result();
    }

    /**
     * 字典项列表-默认授权。
     * @param dataPermissionId 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataAuthority.default")
    @GetMapping("default/dict/list/{dataPermissionId}")
    public Result<List<DictItemVO>> dictListForDefault(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<DictItemDTO> list = (List<DictItemDTO>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.DEFAULT);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Result<List<DictItemVO>> result = new Result<>(voList);
        return result;
    }

    /**
     * 字典项列表-角色。
     * @param dataPermissionId 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataAuthority.role")
    @GetMapping("role/dict/list/{dataPermissionId}")
    public Result<List<DictItemVO>> dictListForRole(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<DictItemDTO> list = (List<DictItemDTO>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.ROLE);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Result<List<DictItemVO>> result = new Result<>(voList);
        return result;
    }

    /**
     * 字典项列表-用户。
     * @param dataPermissionId 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataAuthority.user")
    @GetMapping("user/dict/list/{dataPermissionId}")
    public Result<List<DictItemVO>> dictListForUser(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<DictItemDTO> list = (List<DictItemDTO>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.USER);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Result<List<DictItemVO>> result = new Result<>(voList);
        return result;
    }

    /**
     * 字典项Tree-默认授权。
     * @param dataPermissionId 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataAuthority.default")
    @GetMapping("default/dict/tree/{dataPermissionId}")
    public Result<List<Map<String, Object>>> dictTreeForDefault(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<Map<String, Object>> list = (List<Map<String, Object>>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.DEFAULT);

        Result<List<Map<String, Object>>> result = new Result<>();
        result.setData(list);
        return result;
    }

    /**
     * 字典项Tree-角色。
     * @param dataPermissionId 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataAuthority.role")
    @GetMapping("role/dict/tree/{dataPermissionId}")
    public Result<List<Map<String, Object>>> dictTreeForRole(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<Map<String, Object>> list = (List<Map<String, Object>>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.ROLE);

        Result<List<Map<String, Object>>> result = new Result<>();
        result.setData(list);
        return result;
    }

    /**
     * 字典项Tree-用户。
     * @param dataPermissionId 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataAuthority.user")
    @GetMapping("user/dict/tree/{dataPermissionId}")
    public Result<List<Map<String, Object>>> dictTreeForUser(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<Map<String, Object>> list = (List<Map<String, Object>>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.USER);

        Result<List<Map<String, Object>>> result = new Result<>();
        result.setData(list);
        return result;
    }

    /**
     * 机构Tree-默认授权。
     * @return
     */
    @AccessPermission("sys.dataAuthority.default")
    @GetMapping("default/org/{dataPermissionId}")
    public Result<List<Map<String, Object>>> orgTreeForDefault(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<Map<String, Object>> list = (List<Map<String, Object>>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.DEFAULT);

        Result<List<Map<String, Object>>> result = new Result<>();
        result.setData(list);
        return result;
    }

    /**
     * 机构Tree-角色。
     * @return
     */
    @AccessPermission("sys.dataAuthority.role")
    @GetMapping("role/org/{dataPermissionId}")
    public Result<List<Map<String, Object>>> orgTreeForRole(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<Map<String, Object>> list = (List<Map<String, Object>>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.ROLE);

        Result<List<Map<String, Object>>> result = new Result<>();
        result.setData(list);
        return result;
    }

    /**
     * 机构Tree-用户。
     * @return
     */
    @AccessPermission("sys.dataAuthority.user")
    @GetMapping("user/org/{dataPermissionId}")
    public Result<List<Map<String, Object>>> orgTreeForUser(@PathVariable Long dataPermissionId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        List<Map<String, Object>> list = (List<Map<String, Object>>)dataAuthorityView.getAssignedData(dataPermissionDef, DataAuthorityObjTypeEnum.USER);

        Result<List<Map<String, Object>>> result = new Result<>();
        result.setData(list);
        return result;
    }

    private List<DataAuthorityDetailDTO> convertDetail(String detailList) {
        List<DataAuthorityDetailDTO> details = JsonUtils.fromJson(detailList, new TypeReference<List<DataAuthorityDetailDTO>>() {});
        return details;
    }

}
