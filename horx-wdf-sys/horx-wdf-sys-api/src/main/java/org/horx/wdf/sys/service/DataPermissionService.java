package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;

import java.util.List;

/**
 * 数据权限Service。
 * @since 1.0
 */
public interface DataPermissionService  {

    DataPermissionDefDTO getById(Long id);

    DataPermissionDefDTO getByCode(String code);

    PaginationResult<DataPermissionDefDTO> paginationQuery(PaginationQuery<DataPermissionQueryDTO> paginationQuery);

    List<DataPermissionDefDTO> queryForAuthorityObj(String authorityObjType, Long authorityObjId);

    Long create(DataPermissionDefDTO dataPermissionDTO);

    void modify(DataPermissionDefDTO dataPermissionDTO);

    void remove(Long[] ids);
}
