package org.horx.wdf.sys.service;

import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.springcloud.SpringcloudConsumerContextInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 访问权限Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/accessPermission")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface AccessPermissionService {

    @PostMapping("/queryAuthorizedMenuForUser")
    List<MenuDTO> queryAuthorizedMenuForUser(@RequestParam Long userId, @RequestBody MenuQueryDTO menuQuery);

    @GetMapping("/isPermissionAllowedForUser")
    boolean isPermissionAllowedForUser(@RequestParam Long userId, @RequestParam String permissionCode);

    @GetMapping("/getRoleIdsByPermissionCode")
    Long[] getRoleIdsByPermissionCode(@RequestParam Long userId, @RequestParam String permissionCode);

    @GetMapping("/isMenuAllowedForUser")
    boolean isMenuAllowedForUser(@RequestParam Long userId, @RequestParam String menuCode);

    @GetMapping("/isPermissionAllowedForRole")
    boolean isPermissionAllowedForRole(@RequestParam Long roleId, @RequestParam String permissionCode);

    @PostMapping("/isPermissionAllowedForRoleBatch")
    boolean[] isPermissionAllowedForRoleBatch(@RequestParam Long roleId, @RequestParam String[] permissionCode);
}
