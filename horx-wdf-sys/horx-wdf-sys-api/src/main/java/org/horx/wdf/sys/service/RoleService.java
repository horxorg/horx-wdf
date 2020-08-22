package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.MenuAuthorityDTO;
import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.RoleWithAuthDTO;
import org.horx.wdf.sys.dto.wrapper.SaveMenuPermissionDTO;

import java.util.List;

/**
 * 角色Service。
 * @since 1.0
 */
public interface RoleService {

    RoleDTO getById(Long id);

    RoleDTO getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth);

    RoleDTO getByIdUsable(Long id, SysDataAuthDTO sysDataAuth);

    PaginationResult<RoleDTO> paginationQuery(PaginationQuery<RoleQueryDTO> paginationQuery);

    PaginationResult<RoleDTO> paginationQueryUsable(PaginationQuery<RoleQueryDTO> paginationQuery);

    Long create(RoleWithAuthDTO roleWithAuthDTO);

    void modify(RoleWithAuthDTO roleWithAuthDTO);

    void saveMenuPermission(SaveMenuPermissionDTO saveMenuPermissionDTO);

    void remove(BatchWithSysAuthDTO batchWithAuth);

    List<MenuAuthorityDTO> queryForMenu(Long[] roleIds, String menuAuthorityObjType);
}
