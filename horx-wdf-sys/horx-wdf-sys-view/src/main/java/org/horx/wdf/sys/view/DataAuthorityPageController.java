package org.horx.wdf.sys.view;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.exception.ResultException;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityConfig;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityView;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.horx.wdf.sys.service.DataPermissionService;
import org.horx.wdf.sys.service.RoleService;
import org.horx.wdf.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据授权页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/dataAuthority")
public class DataAuthorityPageController {

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private DataPermissionService dataPermissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DataAuthorityConfig dataAuthorityConfig;

    @Autowired
    private MsgTool msgTool;

    @AccessPermission("sys.dataAuthority.default")
    @RequestMapping("/default/{dataPermissionId}")
    public ModelAndView defaultAuthority(@PathVariable("dataPermissionId") Long dataPermissionId) {
        ModelAndView mav = createModelAndView(dataPermissionId, DataAuthorityObjTypeEnum.DEFAULT, 0L);
        mav.addObject("authorityObjType", DataAuthorityObjTypeEnum.DEFAULT.getCode());
        mav.addObject("authorityObjId", 0L);
        return mav;
    }

    @AccessPermission("sys.dataAuthority.role")
    @RequestMapping("/role/{dataPermissionId}")
    public ModelAndView roleAuthority(@PathVariable("dataPermissionId") Long dataPermissionId,
                                      @RequestParam("objId") Long roleId,
                                      @RequestParam("roleType") String roleType,
                                      @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        RoleDTO roleDTO = roleService.getByIdAuthorized(roleId, sysDataAuth);
        if (roleDTO == null) {
            throw new ResultException(new Result(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden")));
        }

        DataAuthorityObjTypeEnum dataAuthorityObjTypeEnum = DataAuthorityObjTypeEnum.ROLE;
        if (DataAuthorityObjTypeEnum.ADMIN_ROLE.getCode().equals(roleType)) {
            dataAuthorityObjTypeEnum = DataAuthorityObjTypeEnum.ADMIN_ROLE;
        }
        ModelAndView mav = createModelAndView(dataPermissionId, dataAuthorityObjTypeEnum, roleId);
        mav.addObject("authorityObjType", DataAuthorityObjTypeEnum.ROLE.getCode());
        mav.addObject("authorityObjId", roleId);
        mav.addObject("roleType", roleType);
        return mav;
    }

    @AccessPermission("sys.dataAuthority.role.detail")
    @RequestMapping("/role/detail/{dataPermissionId}")
    public ModelAndView roleAuthorityDetail(@PathVariable("dataPermissionId") Long dataPermissionId,
                                      @RequestParam("objId") Long roleId,
                                      @RequestParam("roleType") String roleType,
                                      @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        ModelAndView mav = roleAuthority(dataPermissionId, roleId, roleType, sysDataAuth);
        mav.addObject("detail", "1");
        return mav;
    }

    @AccessPermission("sys.dataAuthority.user")
    @RequestMapping("/user/{dataPermissionId}")
    public ModelAndView userAuthority(@PathVariable("dataPermissionId") Long dataPermissionId,
                                      @RequestParam("objId") Long userId,
                                      @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        UserDTO entity = userService.getByIdAuthorized(userId, sysDataAuth);
        if (entity == null) {
            throw new PermissionDeniedException();
        }

        ModelAndView mav = createModelAndView(dataPermissionId, DataAuthorityObjTypeEnum.USER, userId);
        mav.addObject("authorityObjType", DataAuthorityObjTypeEnum.USER.getCode());
        mav.addObject("authorityObjId", userId);
        return mav;
    }

    @AccessPermission("sys.dataAuthority.role")
    @RequestMapping("/role/list")
    public ModelAndView roleList(@RequestParam() Long roleId, @RequestParam String roleType, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        RoleDTO roleDTO = roleService.getByIdAuthorized(roleId, sysDataAuth);
        if (roleDTO == null) {
            throw new PermissionDeniedException();
        }

        List<DataPermissionDefDTO> defList = dataPermissionService.queryForAuthorityObj(DataAuthorityObjTypeEnum.ROLE.getCode(), roleId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("defList", JsonUtils.toJson(defList));
        mav.addObject("objType", DataAuthorityObjTypeEnum.ROLE.getCode());
        mav.addObject("objId", roleId);
        mav.addObject("roleType", roleType);
        mav.setViewName("sys/dataAuthority/list");

        return mav;
    }

    @AccessPermission("sys.dataAuthority.role.detail")
    @RequestMapping("/role/list/detail")
    public ModelAndView roleListDetail(@RequestParam() Long roleId, @RequestParam String roleType, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        ModelAndView mav = roleList(roleId, roleType, sysDataAuth);
        mav.addObject("detail", "1");
        return mav;
    }

    @AccessPermission("sys.dataAuthority.user")
    @RequestMapping("/user/list")
    public ModelAndView userList(@RequestParam() Long userId, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        UserDTO entity = userService.getByIdAuthorized(userId, sysDataAuth);
        if (entity == null) {
            throw new PermissionDeniedException();
        }

        List<DataPermissionDefDTO> defList = dataPermissionService.queryForAuthorityObj(DataAuthorityObjTypeEnum.USER.getCode(), userId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("defList", JsonUtils.toJson(defList));
        mav.addObject("objType", DataAuthorityObjTypeEnum.USER.getCode());
        mav.addObject("objId", userId);
        mav.setViewName("sys/dataAuthority/list");

        return mav;
    }

    private ModelAndView createModelAndView(Long dataPermissionId, DataAuthorityObjTypeEnum dataAuthorityObjTypeEnum, Long objId) {
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getById(dataPermissionId);
        if (dataPermissionDef == null) {
            throw new ResultException(new Result(ErrorCodeEnum.A0400.getCode(), msgTool.getMsg("common.err.dataNotExist")));
        }

        DataAuthorityView dataAuthorityView = dataAuthorityConfig.getDataAuthorityView(dataPermissionDef);
        if (dataAuthorityView == null) {
            throw new RuntimeException("数据权限" + dataPermissionDef.getObjType() + "没有配置view");
        }

        DataAuthorityDTO dataAuthority = dataAuthorityService.getByObj(dataPermissionId, dataAuthorityObjTypeEnum.getCode(), objId);

        Map<String, Object> dict = new HashMap<>();
        List<Map<String, String>> authorityTypeList = dataAuthorityView.getAuthorityTypeDict(dataPermissionDef, dataAuthorityObjTypeEnum);
        dict.put("authorityType", authorityTypeList);

        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", JsonUtils.toJson(dict));
        mav.addObject("data", JsonUtils.toJson(dataAuthority));
        mav.addObject("dataPermission", dataPermissionDef);
        mav.setViewName(dataAuthorityView.getViewName(dataPermissionDef));
        return mav;
    }
}
