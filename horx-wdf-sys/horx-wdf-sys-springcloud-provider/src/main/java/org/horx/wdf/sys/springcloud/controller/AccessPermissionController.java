package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 访问权限控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/accessPermission")
@RestController
public class AccessPermissionController {

    @Autowired
    private AccessPermissionService accessPermissionService;

    @PostMapping("/queryAuthorizedMenuForUser")
    public List<MenuDTO> queryAuthorizedMenuForUser(@RequestParam Long userId, @RequestBody MenuQueryDTO menuQuery) {
        return accessPermissionService.queryAuthorizedMenuForUser(userId, menuQuery);
    }

    @GetMapping("/isPermissionAllowedForUser")
    public boolean isPermissionAllowedForUser(@RequestParam Long userId, @RequestParam String permissionCode) {
        return accessPermissionService.isPermissionAllowedForUser(userId, permissionCode);
    }

    @GetMapping("/getRoleIdsByPermissionCode")
    public Long[] getRoleIdsByPermissionCode(@RequestParam Long userId, @RequestParam String permissionCode) {
        return accessPermissionService.getRoleIdsByPermissionCode(userId, permissionCode);
    }

    @GetMapping("/isMenuAllowedForUser")
    public boolean isMenuAllowedForUser(@RequestParam Long userId, @RequestParam String menuCode) {
        return accessPermissionService.isMenuAllowedForUser(userId, menuCode);
    }

    @GetMapping("/isPermissionAllowedForRole")
    public boolean isPermissionAllowedForRole(@RequestParam Long roleId, @RequestParam String permissionCode) {
        return accessPermissionService.isPermissionAllowedForRole(roleId, permissionCode);
    }

    @PostMapping("/isPermissionAllowedForRoleBatch")
    public boolean[] isPermissionAllowedForRoleBatch(@RequestParam Long roleId, @RequestParam String[] permissionCode) {
        return accessPermissionService.isPermissionAllowedForRoleBatch(roleId, permissionCode);
    }
}
