package org.horx.wdf.sys.manager;

import org.horx.common.collection.Tree;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;

import java.util.Map;
import java.util.Set;

/**
 * 访问权限Manager。
 * @since 1.0
 */
public interface AccessPermissionManager {

    Tree<Menu, Long> queryAuthorizedMenuForUser(Long userId, MenuQueryDTO menuQuery);

    /**
     * 获取授予用户的权限代码。
     * @param userId 用户ID。
     * @return 权限代码。key为权限代码，value为角色ID集合。
     */
    Map<String, Set<Long>> queryPermissionCodeForUser(Long userId);

    /**
     * 获取授予角色的权限代码。
     * @param roleId 角色ID。
     * @return 权限代码集合。
     */
    Set<String> queryPermissionCodeForRole(Long roleId);

    /**
     * 获取授予用户的菜单代码。
     * @param userId 用户ID。
     * @return 菜单代码。key为菜单代码，value为角色ID集合。
     */
    Map<String, Set<Long>> queryMenuCodeForUser(Long userId);


}
