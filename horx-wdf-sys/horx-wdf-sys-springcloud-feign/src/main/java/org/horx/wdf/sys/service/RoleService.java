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
 * 角色Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/role")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface RoleService {

    @GetMapping("/getById")
    RoleDTO getById(@RequestParam Long id);

    @PostMapping("/getByIdAuthorized")
    RoleDTO getByIdAuthorized(@RequestParam Long id, @RequestBody SysDataAuthDTO sysDataAuth);

    @PostMapping("/getByIdUsable")
    RoleDTO getByIdUsable(@RequestParam Long id, @RequestBody SysDataAuthDTO sysDataAuth);

    @PostMapping("/pagingQuery")
    PagingResult<RoleDTO> pagingQuery(@RequestBody PagingQuery<RoleQueryDTO> pagingQuery);

    @PostMapping("/pagingQueryUsable")
    PagingResult<RoleDTO> pagingQueryUsable(@RequestBody PagingQuery<RoleQueryDTO> pagingQuery);

    @PostMapping("/create")
    Long create(@RequestBody RoleWithAuthDTO roleWithAuthDTO);

    @PostMapping("/modify")
    void modify(@RequestBody RoleWithAuthDTO roleWithAuthDTO);

    @PostMapping("/saveMenuPermission")
    void saveMenuPermission(@RequestBody SaveMenuPermissionDTO saveMenuPermissionDTO);

    @PostMapping("/remove")
    void remove(@RequestBody BatchWithSysAuthDTO batchWithAuth);

    @PostMapping("/queryForMenu")
    List<MenuAuthorityDTO> queryForMenu(@RequestParam Long[] roleIds, @RequestParam String menuAuthorityObjType);
}
