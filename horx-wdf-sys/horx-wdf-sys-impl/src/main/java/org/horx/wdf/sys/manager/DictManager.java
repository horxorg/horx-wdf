package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingResult;
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

    PagingResult<Dict> pagingQuery(DictQueryDTO dictQuery, PagingParam pagingParam);

    void create(Dict dict, DictDataAuthDTO dictDataAuth);

    void modify(Dict dict, DictDataAuthDTO dictDataAuth);

    void remove(Long[] ids, DictDataAuthDTO dictDataAuth);

    DictItem getItemById(Long id);

    PagingResult<DictItem> pagingQueryItem(DictItemQueryDTO dictItemQuery, PagingParam pagingParam);

    List<DictItem> queryItemForTree(DictItemQueryDTO dictItemQuery);

    void createItem(DictItem dictItem);

    void modifyItem(DictItem dictItem);

    void removeItem(Long[] ids);

    List<DictItem> queryEnabledItemList(String dictCode);

    List<DictItem> queryEnabledItemListByDictId(Long dictId);

    List<DictItem> queryEnabledItemListAuthorized(String dictCode, DictItemAuthDTO dictItemAuth);
}
