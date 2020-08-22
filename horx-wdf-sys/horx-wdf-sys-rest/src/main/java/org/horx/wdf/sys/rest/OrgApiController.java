package org.horx.wdf.sys.rest;

import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.SortParam;
import org.horx.wdf.common.entity.SortQuery;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.converter.query.OrgQueryVoConverter;
import org.horx.wdf.sys.converter.OrgVoConverter;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.OrgWithAuthDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.service.OrgService;
import org.horx.wdf.sys.vo.OrgVO;
import org.horx.wdf.sys.vo.query.OrgQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 机构API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/org")
public class OrgApiController {
    private static final Long ROOT_ID = -1L;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgVoConverter orgVoConverter;

    @Autowired
    private OrgQueryVoConverter orgQueryVoConverter;

    @AccessPermission("sys.org.query")
    @PostMapping("/query")
    public Result<List<OrgVO>> query(@ArgEntity OrgQueryVO query, SortParam sortParam) {
        OrgQueryDTO orgQueryDTO = orgQueryVoConverter.fromVo(query);
        SortQuery<OrgQueryDTO> sortQuery = new SortQuery<>(orgQueryDTO, sortParam);
        List<OrgDTO> list = orgService.query(sortQuery);
        List<OrgVO> voList = orgVoConverter.toVoList(list);
        return new Result<>(voList);
    }

    @PostMapping("/queryForTree")
    public Result<List<OrgVO>> queryForTree(@ArgEntity OrgQueryVO query, SortParam sortParam) {
        Result<List<OrgVO>> result = query(query, sortParam);
        Tree<OrgVO, Long> tree = new Tree<>();
        for (OrgVO org : result.getData()) {
            tree.addNode(org, org.getId(), org.getParentId());
        }

        tree.buildTree(ROOT_ID);
        tree.upgradeNoRarentNode();

        List<OrgVO> list = tree.convertToTreeData();
        result.setData(list);
        return result;
    }

    @AccessPermission("sys.org.query")
    @GetMapping("/{id}")
    public Result<OrgVO> getById(@PathVariable("id") Long id, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        OrgDTO orgDTO = orgService.getByIdAuthorized(id, sysDataAuth);
        OrgVO orgVO = orgVoConverter.toVo(orgDTO);
        return new Result<>(orgVO);
    }

    @AccessPermission("sys.org.create")
    @PostMapping()
    public Result<Long> create(@ArgEntity(create = true) OrgVO orgVO, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        if (sysDataAuth != null && sysDataAuth.getOrgAuth().getScope() != DataValidationScopeEnum.ALL.getCode() && orgVO.getParentId() == null) {
            return new Result(ErrorCodeEnum.A0430.getCode(), msgTool.getMsg("sys.org.parent") + ":" + msgTool.getMsg("common.err.param.notEmpty", ""));
        }

        OrgWithAuthDTO orgWithAuthDTO = new OrgWithAuthDTO();
        orgWithAuthDTO.setOrg(orgVoConverter.fromVo(orgVO));
        orgWithAuthDTO.setSysDataAuth(sysDataAuth);

        Long orgId = orgService.create(orgWithAuthDTO);
        return new Result(orgId);
    }

    @AccessPermission("sys.org.modify")
    @PutMapping("/{id}")
    public Result modify(@ArgEntity(modify = true) OrgVO orgVO, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        OrgWithAuthDTO orgWithAuthDTO = new OrgWithAuthDTO();
        orgWithAuthDTO.setOrg(orgVoConverter.fromVo(orgVO));
        orgWithAuthDTO.setSysDataAuth(sysDataAuth);

        orgService.modify(orgWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.org.remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        BatchWithSysAuthDTO batchWithAuthDTO = new BatchWithSysAuthDTO();
        batchWithAuthDTO.setIds(ids);
        batchWithAuthDTO.setSysDataAuth(sysDataAuth);
        orgService.remove(batchWithAuthDTO);
        return new Result();
    }

}
