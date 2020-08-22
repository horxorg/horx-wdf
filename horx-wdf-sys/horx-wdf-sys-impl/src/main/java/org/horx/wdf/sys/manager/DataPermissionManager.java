package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
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

    PaginationResult<DataPermissionDef> paginationQuery(DataPermissionQueryDTO dataPermissionQuery,
                                                        PaginationParam paginationParam);

    List<DataPermissionDef> queryForAuthorityObj(String authorityObjType, Long authorityObjId);

    void create(DataPermissionDef dataPermission);

    void modify(DataPermissionDef dataPermission);

    void remove(Long[] ids);
}
