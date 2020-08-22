package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithDictAuthDTO;
import org.horx.wdf.sys.dto.wrapper.DictWithAuthDTO;

import java.util.List;

/**
 * 字典Service。
 * @since 1.0
 */
public interface DictService {
    DictDTO getById(Long id);

    DictDTO getByIdAuthorized(Long id, DictDataAuthDTO dictDataAuth);

    DictDTO getByCode(String code);

    PaginationResult<DictDTO> paginationQuery(PaginationQuery<DictQueryDTO> paginationQuery);

    Long create(DictWithAuthDTO dictWithAuthDTO);

    void modify(DictWithAuthDTO dictWithAuthDTO);

    void remove(BatchWithDictAuthDTO batchWithDictAuthDTO);

    DictItemDTO getItemById(Long id);

    PaginationResult<DictItemDTO> paginationQueryItem(PaginationQuery<DictItemQueryDTO> paginationQuery);

    Long createItem(DictItemDTO dictItem);

    void modifyItem(DictItemDTO dictItem);

    void removeItem(Long[] ids);

    List<DictItemDTO> queryEnabledItemList(String dictCode);

    List<DictItemDTO> queryEnabledItemListByDictId(Long dictId);

    List<DictItemDTO> queryEnabledItemListAuthorized(String dictCode, DictItemAuthDTO dictItemAuth);
}
