package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
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

    PagingResult<RoleDTO> pagingQuery(PagingQuery<RoleQueryDTO> pagingQuery);

    PagingResult<RoleDTO> pagingQueryUsable(PagingQuery<RoleQueryDTO> pagingQuery);

    Long create(RoleWithAuthDTO roleWithAuthDTO);

    void modify(RoleWithAuthDTO roleWithAuthDTO);

    void saveMenuPermission(SaveMenuPermissionDTO saveMenuPermissionDTO);

    void remove(BatchWithSysAuthDTO batchWithAuth);

    List<MenuAuthorityDTO> queryForMenu(Long[] roleIds, String menuAuthorityObjType);
}
