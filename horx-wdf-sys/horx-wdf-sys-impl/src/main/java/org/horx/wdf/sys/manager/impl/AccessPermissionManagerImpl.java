package org.horx.wdf.sys.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.common.collection.Tree;
import org.horx.common.collection.TreeNode;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.enums.CheckedTypeEnum;
import org.horx.wdf.sys.enums.MenuAuthorityObjTypeEnum;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.domain.MenuAuthority;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.domain.UserRole;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.manager.AccessPermissionManager;
import org.horx.wdf.sys.manager.MenuManager;
import org.horx.wdf.sys.manager.RoleManager;
import org.horx.wdf.sys.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 访问权限Manager实现。
 * @since 1.0
 */
@Component("permissionManager")
public class AccessPermissionManagerImpl implements AccessPermissionManager {

    @Autowired
    private UserManager userManager;

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private SysConfig sysConfig;

    @Transactional(readOnly = true)
    public Tree<Menu, Long> queryAuthorizedMenuForUser(Long userId, MenuQueryDTO menuQuery) {
        return queryForAuthorizedMenuInternal(userId, menuQuery).getMenuTree();
    }

    /**
     * 查询授予用户的所有权限项。
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "sysCache", key = "'sys.usr.perm.' + #userId")
    public Map<String, Set<Long>> queryPermissionCodeForUser(Long userId) {
        MenuQueryDTO menuQuery = new MenuQueryDTO();
        menuQuery.setEnabled(true);
        AuthorizedMenu authorizedMenu = queryForAuthorizedMenuInternal(userId, menuQuery);

        Map<String, Set<Long>> permissionCodeMap = new HashMap<>();

        processPermissionCode(authorizedMenu, authorizedMenu.getMenuTree().getRoots(), permissionCodeMap);
        return permissionCodeMap;
    }


    @Override
    @Transactional(readOnly = true)
    public Set<String> queryPermissionCodeForRole(Long roleId) {
        MenuQueryDTO menuQuery = new MenuQueryDTO();
        menuQuery.setEnabled(true);
        Tree<Menu, Long> tree = menuManager.queryForTree(menuQuery);

        List<MenuAuthority> menuAuthorityList = roleManager.queryForMenu(new Long[]{roleId}, MenuAuthorityObjTypeEnum.ROLE);
        for (MenuAuthority menuAuthority : menuAuthorityList) {
            TreeNode<Menu, Long> node = tree.getNode(menuAuthority.getMenuId());
            if (node == null) {
                continue;
            }
            if (menuAuthority.getCheckedType() == CheckedTypeEnum.CHECKED.getCode()) {
                node.check();
            } else if (menuAuthority.getCheckedType() == CheckedTypeEnum.CHECKED_ALL.getCode()) {
                node.checkAll();
            }
        }

        Set<String> permissionCodeSet = new HashSet<>();
        processPermissionCode(tree.getRoots(), permissionCodeSet);
        return permissionCodeSet;
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "sysCache", key = "'sys.usr.menu.' + #userId")
    public Map<String, Set<Long>> queryMenuCodeForUser(Long userId) {
        MenuQueryDTO menuQuery = new MenuQueryDTO();
        menuQuery.setEnabled(true);
        AuthorizedMenu authorizedMenu = queryForAuthorizedMenuInternal(userId, menuQuery);

        Map<String, Set<Long>> menuCodeMap = new HashMap<>();

        processMenuCode(authorizedMenu, authorizedMenu.getMenuTree().getRoots(), menuCodeMap);
        return menuCodeMap;
    }

    private AuthorizedMenu queryForAuthorizedMenuInternal(Long userId, MenuQueryDTO menuQuery) {
        User user = userManager.getById(userId);
        if (user == null) {
            return null;
        }

        AuthorizedMenu authorizedMenu = new AuthorizedMenu();
        Tree<Menu, Long> tree = menuManager.queryForTree(menuQuery);
        authorizedMenu.setMenuTree(tree);

        List<UserRole> roleList = userManager.queryForValidRoles(userId);
        if (roleList.size() > 0) {
            Long[] roleIds = new Long[roleList.size()];
            for (int i = 0; i < roleList.size(); i++) {
                roleIds[i] = roleList.get(i).getRoleId();
            }
            List<MenuAuthority> menuAuthorityList = roleManager.queryForMenu(roleIds, MenuAuthorityObjTypeEnum.ROLE);
            for (MenuAuthority menuAuthority : menuAuthorityList) {
                TreeNode<Menu, Long> node = tree.getNode(menuAuthority.getMenuId());
                if (node == null) {
                    continue;
                }
                if (menuAuthority.getCheckedType() == CheckedTypeEnum.CHECKED.getCode()) {
                    node.check();
                    authorizedMenu.addRoleId(menuAuthority.getMenuId(), menuAuthority.getObjId());
                } else if (menuAuthority.getCheckedType() == CheckedTypeEnum.CHECKED_ALL.getCode()) {
                    node.checkAll();

                    List<TreeNode<Menu, Long>> subs = node.getSelfAndSubNodes();
                    for (TreeNode<Menu, Long> subNode : subs) {
                        authorizedMenu.addRoleId(subNode.getData().getId(), menuAuthority.getObjId());
                    }
                }
            }
        }

        if (sysConfig.isAdmin(user.getUsername())) {
            if (sysConfig.isAdminHasAllMenuPermission()) {
                List<TreeNode<Menu, Long>> roots = tree.getRoots();
                for (TreeNode<Menu, Long> node : roots) {
                    node.checkAll();
                }
            } else {
                Collection<TreeNode<Menu, Long>> allNodes = tree.getAllNodes();
                for (TreeNode<Menu, Long> node : allNodes) {
                    if (Boolean.TRUE.equals(node.getData().getBuiltIn())) {
                        node.checkAll();
                    }
                }
            }
        }

        return authorizedMenu;
    }



    private void processPermissionCode(AuthorizedMenu authorizedMenu, List<TreeNode<Menu, Long>> nodeList, Map<String, Set<Long>> permissionCodeMap) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }

        for (TreeNode<Menu, Long> node : nodeList) {
            if (node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
                continue;
            }
            Menu menu = node.getData();
            String code = menu.getPermissionCode();
            if (StringUtils.isNotEmpty(code)) {
                String[] arr = code.split(",");
                for (String str : arr) {
                    processCode(authorizedMenu, menu, permissionCodeMap, str);
                }
            }
            processPermissionCode(authorizedMenu, node.getChildren(), permissionCodeMap);
        }
    }

    private void processPermissionCode(List<TreeNode<Menu, Long>> nodeList, Set<String> permissionCodeSet) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }

        for (TreeNode<Menu, Long> node : nodeList) {
            if (node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
                continue;
            }
            Menu menu = node.getData();
            String code = menu.getPermissionCode();
            if (StringUtils.isNotEmpty(code)) {
                String[] arr = code.split(",");
                for (String str : arr) {
                    if (StringUtils.isNotEmpty(str)) {
                        str = str.trim();
                        permissionCodeSet.add(str);
                    }
                }
            }

            processPermissionCode(node.getChildren(), permissionCodeSet);
        }
    }

    private void processMenuCode(AuthorizedMenu authorizedMenu, List<TreeNode<Menu, Long>> nodeList, Map<String, Set<Long>> menuCodeMap) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }

        for (TreeNode<Menu, Long> node : nodeList) {
            if (node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
                continue;
            }
            Menu menu = node.getData();
            String code = menu.getCode();
            processCode(authorizedMenu, menu, menuCodeMap, code);

            processPermissionCode(authorizedMenu, node.getChildren(), menuCodeMap);
        }
    }

    private void processCode(AuthorizedMenu authorizedMenu, Menu menu, Map<String, Set<Long>> codeMap, String code) {
        if (StringUtils.isEmpty(code)) {
            return;
        }

        code = code.trim();
        if (codeMap.containsKey(code)) {
            Set<Long> roleIdSet = authorizedMenu.getRoleIds(menu.getId());
            if (roleIdSet != null) {
                Set<Long> currSet = codeMap.get(code);
                if (currSet == null) {
                    codeMap.put(code, roleIdSet);
                } else {
                    currSet.addAll(roleIdSet);
                }
            }
        } else {
            codeMap.put(code, authorizedMenu.getRoleIds(menu.getId()));
        }
    }

    private static class AuthorizedMenu {
        private Tree<Menu, Long> menuTree;

        /**
         * key为menuId，value为roleId的Set。
         */
        private Map<Long, Set<Long>> menuAuthorizedRoleIds = new HashMap<>();

        public Tree<Menu, Long> getMenuTree() {
            return menuTree;
        }

        public void setMenuTree(Tree<Menu, Long> menuTree) {
            this.menuTree = menuTree;
        }

        public void addRoleId(Long menuId, Long roleId) {
            Set<Long> roleIdSet = menuAuthorizedRoleIds.get(menuId);
            if (roleIdSet == null) {
                roleIdSet = new HashSet<>();
                menuAuthorizedRoleIds.put(menuId, roleIdSet);
            }
            roleIdSet.add(roleId);
        }

        public Set<Long> getRoleIds(Long menuId) {
            return menuAuthorizedRoleIds.get(menuId);
        }
    }

}
