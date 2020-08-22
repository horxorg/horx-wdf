package org.horx.wdf.sys.rest;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.converter.DataPermissionDefVoConverter;
import org.horx.wdf.sys.converter.query.DataPermissionQueryVoConverter;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.enums.DataPermissionObjTypeEnum;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;
import org.horx.wdf.sys.service.DataPermissionService;
import org.horx.wdf.sys.vo.DataPermissionDefVO;
import org.horx.wdf.sys.vo.query.DataPermissionQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据权限定义API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/dataPermission")
public class DataPermissionApiController {

    @Autowired
    private DataPermissionService dataPermissionService;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private DataPermissionDefVoConverter dataPermissionDefVoConverter;

    @Autowired
    private DataPermissionQueryVoConverter dataPermissionQueryVoConverter;

    /**
     * 分页查询。
     * @param query 查询条件。
     * @param paginationParam 分页参数。
     * @return
     */
    @AccessPermission("sys.dataPermission.query")
    @PostMapping("/paginationQuery")
    public PaginationResult<DataPermissionDefVO> paginationQuery(@ArgEntity DataPermissionQueryVO query, PaginationParam paginationParam) {
        DataPermissionQueryDTO dataPermissionQueryDTO = dataPermissionQueryVoConverter.fromVo(query);
        PaginationQuery<DataPermissionQueryDTO> paginationQuery = new PaginationQuery<>(dataPermissionQueryDTO, paginationParam);
        PaginationResult<DataPermissionDefDTO> paginationResult = dataPermissionService.paginationQuery(paginationQuery);
        PaginationResult<DataPermissionDefVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(dataPermissionDefVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }

    /**
     * 根据ID获取内容。
     * @param id 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataPermission.query")
    @GetMapping("/{id}")
    public Result<DataPermissionDefVO> getById(@PathVariable("id") Long id) {
        DataPermissionDefDTO dto = dataPermissionService.getById(id);
        DataPermissionDefVO vo = dataPermissionDefVoConverter.toVo(dto);
        return new Result<>(vo);
    }

    /**
     * 新建。
     * @param vo 数据权限定义。
     * @return
     */
    @AccessPermission("sys.dataPermission.create")
    @PostMapping()
    public Result<Long> create(@ArgEntity(create = true) DataPermissionDefVO vo) {
        if (DataPermissionObjTypeEnum.DICT.getCode().equals(vo.getObjType())) {
            if (vo.getObjId() == null) {
                return new Result(ErrorCodeEnum.A0430.getCode(), msgTool.getMsg("sys.dataPermission.obj") + ":" + msgTool.getMsg("common.err.param.notEmpty", ""));
            }
        } else {
            vo.setObjId(null);
        }

        DataPermissionDefDTO dto = dataPermissionDefVoConverter.fromVo(vo);
        Long id = dataPermissionService.create(dto);
        return new Result(id);
    }

    /**
     * 修改。
     * @param vo 数据权限定义。
     * @return
     */
    @AccessPermission("sys.dataPermission.modify")
    @PutMapping("/{id}")
    public Result modify(@ArgEntity(modify = true) DataPermissionDefVO vo) {
        DataPermissionDefDTO dto = dataPermissionDefVoConverter.fromVo(vo);
        dataPermissionService.modify(dto);
        return new Result();
    }

    /**
     * 删除。
     * @param ids 数据权限定义id数组。
     * @return
     */
    @AccessPermission("sys.dataPermission.remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids) {
        dataPermissionService.remove(ids);
        return new Result();
    }
}
