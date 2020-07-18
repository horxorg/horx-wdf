package org.horx.wdf.sys.service;

import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;

import java.util.List;

/**
 * 访问权限Service。
 * @since 1.0
 */
public interface AccessPermissionService {

    List<MenuDTO> queryAuthorizedMenuForUser(Long userId, MenuQueryDTO menuQuery);

    boolean isPermissionAllowedForUser(Long userId, String permissionCode);

    Long[] getRoleIdsByPermissionCode(Long userId, String permissionCode);

    boolean isMenuAllowedForUser(Long userId, String menuCode);

    boolean isPermissionAllowedForRole(Long roleId, String permissionCode);

    boolean[] isPermissionAllowedForRoleBatch(Long roleId, String[] permissionCode);
}
