package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.domain.Dict;
import org.horx.wdf.sys.domain.DictItem;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;

import java.util.List;

/**
 * 字典Manager。
 * @since 1.0
 */
public interface DictManager {
    Dict getById(Long id);

    Dict getByIdAuthorized(Long id, DictDataAuthDTO dictDataAuth);

    Dict getByCode(String code);

    PaginationResult<Dict> paginationQuery(DictQueryDTO dictQuery, PaginationParam paginationParam);

    void create(Dict dict, DictDataAuthDTO dictDataAuth);

    void modify(Dict dict, DictDataAuthDTO dictDataAuth);

    void remove(Long[] ids, DictDataAuthDTO dictDataAuth);

    DictItem getItemById(Long id);

    PaginationResult<DictItem> paginationQueryItem(DictItemQueryDTO dictItemQuery, PaginationParam paginationParam);

    List<DictItem> queryItemForTree(DictItemQueryDTO dictItemQuery);

    void createItem(DictItem dictItem);

    void modifyItem(DictItem dictItem);

    void removeItem(Long[] ids);

    List<DictItem> queryEnabledItemList(String dictCode);

    List<DictItem> queryEnabledItemListByDictId(Long dictId);

    List<DictItem> queryEnabledItemListAuthorized(String dictCode, DictItemAuthDTO dictItemAuth);
}
