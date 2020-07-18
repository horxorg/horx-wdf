package org.horx.wdf.sys.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.common.collection.Tree;
import org.horx.wdf.sys.converter.MenuConverter;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.domain.UserRole;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.manager.AccessPermissionManager;
import org.horx.wdf.sys.manager.UserManager;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 访问权限Service实现。
 * @since 1.0
 */
@Service("accessPermissionService")
public class AccessPermissionServiceImpl implements AccessPermissionService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private AccessPermissionManager accessPermissionManager;

    @Autowired
    private MenuConverter menuConverter;

    @Override
    public List<MenuDTO> queryAuthorizedMenuForUser(Long userId, MenuQueryDTO menuQuery) {
        Tree<Menu, Long> tree = accessPermissionManager.queryAuthorizedMenuForUser(userId, menuQuery);
        List<Menu> list = tree.convertToTreeData(true);
        List<MenuDTO> dtoList = new ArrayList<>();
        for (Menu menu : list) {
            convererMenu(menu, dtoList);
        }
        return dtoList;
    }

    @Override
    public boolean isPermissionAllowedForUser(Long userId, String permissionCode) {
        Map<String, Set<Long>> permissionSet = accessPermissionManager.queryPermissionCodeForUser(userId);
        return permissionSet.containsKey(permissionCode);
    }

    @Override
    public Long[] getRoleIdsByPermissionCode(Long userId, String permissionCode) {
        Long[] roleIds;
        if (StringUtils.isEmpty(permissionCode)) {
            List<UserRole> list = userManager.queryForValidRoles(userId);
            roleIds = new Long[list.size()];
            for (int i = 0; i < list.size(); i++) {
                roleIds[i] = list.get(i).getRoleId();
            }
        } else {
            Map<String, Set<Long>> permissionCodes = accessPermissionManager.queryPermissionCodeForUser(userId);
            Set<Long> roleSet = permissionCodes.get(permissionCode);
            roleIds = (roleSet == null) ? new Long[0] : roleSet.toArray(new Long[0]);
        }

        return roleIds;
    }

    @Override
    public boolean isMenuAllowedForUser(Long userId, String menuCode) {
        Map<String, Set<Long>> menuSet = accessPermissionManager.queryMenuCodeForUser(userId);
        return menuSet.containsKey(menuCode);
    }

    @Override
    public boolean isPermissionAllowedForRole(Long roleId, String permissionCode) {
        Set<String> permissionCodeSet = accessPermissionManager.queryPermissionCodeForRole(roleId);
        boolean exist = permissionCodeSet.contains(permissionCode);
        return exist;
    }

    @Override
    public boolean[] isPermissionAllowedForRoleBatch(Long roleId, String[] permissionCodes) {
        if (permissionCodes == null || permissionCodes.length == 0) {
            return new boolean[0];
        }

        Set<String> permissionCodeSet = accessPermissionManager.queryPermissionCodeForRole(roleId);
        boolean[] arr = new boolean[permissionCodes.length];
        for (int i = 0; i < permissionCodes.length; i++) {
            arr[i] = permissionCodeSet.contains(permissionCodes[i]);
        }
        return arr;
    }

    private void convererMenu(Menu menu, List<MenuDTO> dtoList) {
        if (menu == null) {
            return;
        }

        dtoList.add(menuConverter.toDto(menu));

        List<Menu> children = menu.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (Menu subMenu : children) {
                convererMenu(subMenu, dtoList);
            }
        }
    }
}
