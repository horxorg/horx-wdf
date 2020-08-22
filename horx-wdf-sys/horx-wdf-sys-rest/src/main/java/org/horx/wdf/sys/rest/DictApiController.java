package org.horx.wdf.sys.rest;

import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.common.enums.SortEnum;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.converter.DictItemVoConverter;
import org.horx.wdf.sys.converter.DictVoConverter;
import org.horx.wdf.sys.converter.query.DictItemQueryVoConverter;
import org.horx.wdf.sys.converter.query.DictQueryVoConverter;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithDictAuthDTO;
import org.horx.wdf.sys.dto.wrapper.DictWithAuthDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.service.DictService;
import org.horx.wdf.sys.vo.DictItemVO;
import org.horx.wdf.sys.vo.DictVO;
import org.horx.wdf.sys.vo.query.DictItemQueryVO;
import org.horx.wdf.sys.vo.query.DictQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/dict")
public class DictApiController {

    private static final Long ROOT_ID = -1L;

    @Autowired
    private DictService dictService;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private DictVoConverter dictVoConverter;

    @Autowired
    private DictItemVoConverter dictItemVoConverter;

    @Autowired
    private DictQueryVoConverter dictQueryVoConverter;

    @Autowired
    private DictItemQueryVoConverter dictItemQueryVoConverter;

    @AccessPermission("sys.dict.query")
    @PostMapping("/paginationQuery")
    public PaginationResult<DictVO> paginationQuery(@ArgEntity DictQueryVO query, PaginationParam paginationParam) {
        if (query.getDictDataAuth() != null && query.getDictDataAuth().getScope() == DataValidationScopeEnum.FORBIDDEN.getCode()) {
            return PaginationResult.empty();
        }

        DictQueryDTO dictQueryDTO = dictQueryVoConverter.fromVo(query);
        PaginationQuery<DictQueryDTO> paginationQuery = new PaginationQuery<>(dictQueryDTO, paginationParam);
        PaginationResult<DictDTO> paginationResult = dictService.paginationQuery(paginationQuery);
        PaginationResult<DictVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(dictVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }

    @AccessPermission("sys.dict.query")
    @GetMapping("/{id}")
    public Result<DictVO> getById(@PathVariable("id") Long id, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(id, dictDataAuth);
        DictVO dictVO = dictVoConverter.toVo(dictDTO);
        return new Result<>(dictVO);
    }

    @AccessPermission("sys.dict.create")
    @PostMapping()
    public Result<Long> create(@ArgEntity(create = true) DictVO dictVO, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictWithAuthDTO dictWithAuthDTO = new DictWithAuthDTO();
        dictWithAuthDTO.setDict(dictVoConverter.fromVo(dictVO));
        dictWithAuthDTO.setDictDataAuth(dictDataAuth);

        Long id = dictService.create(dictWithAuthDTO);
        return new Result(id);
    }

    @AccessPermission("sys.dict.modify")
    @PutMapping("/{id}")
    public Result modify(@ArgEntity(modify = true) DictVO dictVO, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictWithAuthDTO dictWithAuthDTO = new DictWithAuthDTO();
        dictWithAuthDTO.setDict(dictVoConverter.fromVo(dictVO));
        dictWithAuthDTO.setDictDataAuth(dictDataAuth);

        dictService.modify(dictWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.dict.remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        BatchWithDictAuthDTO batchWithAuthDTO = new BatchWithDictAuthDTO();
        batchWithAuthDTO.setIds(ids);
        batchWithAuthDTO.setDictDataAuth(dictDataAuth);

        dictService.remove(batchWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.dict.query")
    @PostMapping("/{dictId}/item/paginationQuery")
    public PaginationResult<DictItemVO> paginationQuery(@ArgEntity DictItemQueryVO query, PaginationParam paginationParam, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(query.getDictId(), dictDataAuth);
        if (dictDTO == null) {
            return new PaginationResult<>(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DictItemQueryDTO dictItemQueryDTO = dictItemQueryVoConverter.fromVo(query);
        PaginationQuery<DictItemQueryDTO> paginationQuery = new PaginationQuery<>(dictItemQueryDTO, paginationParam);
        PaginationResult<DictItemDTO> paginationResult = dictService.paginationQueryItem(paginationQuery);
        PaginationResult<DictItemVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(dictItemVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }

    @AccessPermission("sys.dict.query")
    @PostMapping("/{dictId}/item/queryForTree")
    public Result<List<DictItemVO>> queryForTree(@ArgEntity DictItemQueryVO query, PaginationParam paginationParam, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(query.getDictId(), dictDataAuth);
        if (dictDTO == null) {
            return new Result<>(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DictItemQueryDTO dictItemQueryDTO = dictItemQueryVoConverter.fromVo(query);
        if (paginationParam == null) {
            paginationParam = new PaginationParam();
        }
        paginationParam.setPageSize(-1);
        paginationParam.setCurrPage(1);
        if (paginationParam.getSortField() == null || paginationParam.getSortField().length == 0) {
            paginationParam.setSortField(new String[] {"displaySeq"});
            paginationParam.setSortOrder(new String[] {SortEnum.ASC.name()});
        }

        PaginationQuery<DictItemQueryDTO> paginationQuery = new PaginationQuery<>(dictItemQueryDTO, paginationParam);
        PaginationResult<DictItemDTO> paginationResult = dictService.paginationQueryItem(paginationQuery);
        List<DictItemVO> itemVOList = dictItemVoConverter.toVoList(paginationResult.getData());

        Result<List<DictItemVO>> result = new Result<>();
        if (CollectionUtils.isEmpty(itemVOList)) {
            result.setData(new ArrayList<>(0));
            return result;
        }

        Tree<DictItemVO, Long> tree = new Tree<>();
        for (DictItemVO dictItem : itemVOList) {
            tree.addNode(dictItem, dictItem.getId(), dictItem.getParentId());
        }

        tree.buildTree(ROOT_ID);
        tree.upgradeNoRarentNode();

        List<DictItemVO> list = tree.convertToTreeData();
        result.setData(list);
        return result;
    }

    @AccessPermission("sys.dict.query")
    @GetMapping("/{dictId}/item/{id}")
    public Result<DictItemVO> getItemById(@PathVariable("id") Long id, @PathVariable("dictId") Long dictId, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(dictId, dictDataAuth);
        if (dictDTO == null) {
            return new Result<>(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DictItemDTO itemDTO = dictService.getItemById(id);
        DictItemVO itemVO = dictItemVoConverter.toVo(itemDTO);
        return new Result<>(itemVO);
    }

    @AccessPermission("sys.dict.item")
    @PostMapping("/{dictId}/item")
    public Result<Long> createItem(@ArgEntity(create = true) DictItemVO itemVO, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(itemVO.getDictId(), dictDataAuth);
        if (dictDTO == null) {
            return new Result<>(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DictItemDTO itemDTO = dictItemVoConverter.fromVo(itemVO);
        Long itemId = dictService.createItem(itemDTO);
        return new Result(itemId);
    }

    @AccessPermission("sys.dict.item")
    @PutMapping("/{dictId}/item/{id}")
    public Result modifyItem(@ArgEntity(modify = true) DictItemVO itemVO, @PathVariable("dictId") Long dictId, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(dictId, dictDataAuth);
        if (dictDTO == null) {
            return new Result<>(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        DictItemDTO itemDTO = dictItemVoConverter.fromVo(itemVO);
        dictService.modifyItem(itemDTO);
        return new Result();
    }

    @AccessPermission("sys.dict.item")
    @DeleteMapping("/{dictId}/item/{ids}")
    public Result removeItem(@PathVariable Long[] ids, @PathVariable("dictId") Long dictId, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        DictDTO dictDTO = dictService.getByIdAuthorized(dictId, dictDataAuth);
        if (dictDTO == null) {
            return new Result<>(ErrorCodeEnum.A0300.getCode(), msgTool.getMsg("common.err.forbidden"));
        }

        dictService.removeItem(ids);
        return new Result();
    }
}
