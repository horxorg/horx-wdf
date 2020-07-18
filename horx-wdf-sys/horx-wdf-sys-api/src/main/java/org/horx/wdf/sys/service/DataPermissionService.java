package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
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

    PagingResult<DataPermissionDefDTO> pagingQuery(PagingQuery<DataPermissionQueryDTO> pagingQuery);

    List<DataPermissionDefDTO> queryForAuthorityObj(String authorityObjType, Long authorityObjId);

    Long create(DataPermissionDefDTO dataPermissionDTO);

    void modify(DataPermissionDefDTO dataPermissionDTO);

    void remove(Long[] ids);
}
