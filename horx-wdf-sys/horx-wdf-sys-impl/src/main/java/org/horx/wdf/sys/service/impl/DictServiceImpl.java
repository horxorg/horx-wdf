package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.converter.DictConverter;
import org.horx.wdf.sys.converter.DictItemConverter;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithDictAuthDTO;
import org.horx.wdf.sys.dto.wrapper.DictWithAuthDTO;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.domain.Dict;
import org.horx.wdf.sys.domain.DictItem;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.manager.DictManager;
import org.horx.wdf.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典Service实现。
 * @since 1.0
 */
@Service("dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    private DictManager dictManager;

    @Autowired
    private DictConverter dictConverter;

    @Autowired
    private DictItemConverter dictItemConverter;

    @Override
    public DictDTO getById(Long id) {
        Dict dict = dictManager.getById(id);
        DictDTO dictDTO = dictConverter.toDto(dict);
        return dictDTO;
    }

    @Override
    public DictDTO getByIdAuthorized(Long id, DictDataAuthDTO dictDataAuth) {
        Dict dict = dictManager.getByIdAuthorized(id, dictDataAuth);
        DictDTO dictDTO = dictConverter.toDto(dict);
        return dictDTO;
    }

    @Override
    public DictDTO getByCode(String code) {
        Dict dict = dictManager.getByCode(code);
        DictDTO dictDTO = dictConverter.toDto(dict);
        return dictDTO;
    }

    @Override
    public PaginationResult<DictDTO> paginationQuery(PaginationQuery<DictQueryDTO> paginationQuery) {
        PaginationResult<Dict> paginationResult = dictManager.paginationQuery(paginationQuery.getQuery(), paginationQuery.getPaginationParam());

        PaginationResult<DictDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<DictDTO> dtoList = dictConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
    }

    @Override
    public Long create(DictWithAuthDTO dictWithAuthDTO) {
        DictDTO dictDTO = dictWithAuthDTO.getDict();
        Dict dict = dictConverter.fromDto(dictDTO);
        dictManager.create(dict, dictWithAuthDTO.getDictDataAuth());
        return dict.getId();
    }

    @Override
    public void modify(DictWithAuthDTO dictWithAuthDTO) {
        DictDTO dictrDTO = dictWithAuthDTO.getDict();
        Dict dict = dictConverter.fromDto(dictrDTO);
        dictManager.modify(dict, dictWithAuthDTO.getDictDataAuth());
    }

    @Override
    public void remove(BatchWithDictAuthDTO batchWithDictAuthDTO) {
        dictManager.remove(batchWithDictAuthDTO.getIds(), batchWithDictAuthDTO.getDictDataAuth());
    }

    @Override
    public DictItemDTO getItemById(Long id) {
        DictItem dictItem = dictManager.getItemById(id);
        DictItemDTO dictItemDTO = dictItemConverter.toDto(dictItem);
        return dictItemDTO;
    }

    @Override
    public PaginationResult<DictItemDTO> paginationQueryItem(PaginationQuery<DictItemQueryDTO> paginationQuery) {
        PaginationResult<DictItem> paginationResult = dictManager.paginationQueryItem(paginationQuery.getQuery(), paginationQuery.getPaginationParam());

        PaginationResult<DictItemDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<DictItemDTO> dtoList = dictItemConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
    }

    @Override
    public Long createItem(DictItemDTO dictItemDTO) {
        DictItem dictItem = dictItemConverter.fromDto(dictItemDTO);
        dictManager.createItem(dictItem);
        return dictItem.getId();
    }

    @Override
    public void modifyItem(DictItemDTO dictItemDTO) {
        DictItem dictItem = dictItemConverter.fromDto(dictItemDTO);
        dictManager.modifyItem(dictItem);
    }

    @Override
    public void removeItem(Long[] ids) {
        dictManager.removeItem(ids);
    }

    @Override
    public List<DictItemDTO> queryEnabledItemList(String dictCode) {
        List<DictItem> list = dictManager.queryEnabledItemList(dictCode);
        List<DictItemDTO> dtoList = dictItemConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public List<DictItemDTO> queryEnabledItemListByDictId(Long dictId) {
        List<DictItem> list = dictManager.queryEnabledItemListByDictId(dictId);
        List<DictItemDTO> dtoList = dictItemConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public List<DictItemDTO> queryEnabledItemListAuthorized(String dictCode, DictItemAuthDTO dictItemAuth) {
        List<DictItem> list = dictManager.queryEnabledItemListAuthorized(dictCode, dictItemAuth);
        List<DictItemDTO> dtoList = dictItemConverter.toDtoList(list);
        return dtoList;
    }
}
