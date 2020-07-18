package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.domain.DataPermissionDef;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;

import java.util.List;

/**
 * 数据权限Manager。
 * @since 1.0
 */
public interface DataPermissionManager {

    DataPermissionDef getById(Long id);

    DataPermissionDef getByCode(String code);

    PagingResult<DataPermissionDef> pagingQuery(DataPermissionQueryDTO dataPermissionQuery, PagingParam pagingParam);

    List<DataPermissionDef> queryForAuthorityObj(String authorityObjType, Long authorityObjId);

    void create(DataPermissionDef dataPermission);

    void modify(DataPermissionDef dataPermission);

    void remove(Long[] ids);
}
