package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.MenuAuthorityDTO;
import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.RoleWithAuthDTO;
import org.horx.wdf.sys.dto.wrapper.SaveMenuPermissionDTO;
import org.horx.wdf.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getById")
    public RoleDTO getById(@RequestParam Long id) {
        return roleService.getById(id);
    }

    @PostMapping("/getByIdAuthorized")
    public RoleDTO getByIdAuthorized(@RequestParam Long id, @RequestBody SysDataAuthDTO sysDataAuth) {
        return roleService.getByIdAuthorized(id, sysDataAuth);
    }

    @PostMapping("/getByIdUsable")
    public RoleDTO getByIdUsable(@RequestParam Long id, @RequestBody SysDataAuthDTO sysDataAuth) {
        return roleService.getByIdUsable(id, sysDataAuth);
    }

    @PostMapping("/pagingQuery")
    public PagingResult<RoleDTO> pagingQuery(@RequestBody PagingQuery<RoleQueryDTO> pagingQuery) {
        return roleService.pagingQuery(pagingQuery);
    }

    @PostMapping("/pagingQueryUsable")
    public PagingResult<RoleDTO> pagingQueryUsable(@RequestBody PagingQuery<RoleQueryDTO> pagingQuery) {
        return roleService.pagingQueryUsable(pagingQuery);
    }

    @PostMapping("/create")
    public Long create(@RequestBody RoleWithAuthDTO roleWithAuthDTO) {
        return roleService.create(roleWithAuthDTO);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody RoleWithAuthDTO roleWithAuthDTO) {
        roleService.modify(roleWithAuthDTO);
    }

    @PostMapping("/saveMenuPermission")
    public void saveMenuPermission(@RequestBody SaveMenuPermissionDTO saveMenuPermissionDTO) {
        roleService.saveMenuPermission(saveMenuPermissionDTO);
    }

    @PostMapping("/remove")
    public void remove(@RequestBody BatchWithSysAuthDTO batchWithAuth) {
        roleService.remove(batchWithAuth);
    }

    @PostMapping("/queryForMenu")
    public List<MenuAuthorityDTO> queryForMenu(@RequestParam Long[] roleIds, @RequestParam String menuAuthorityObjType) {
        return roleService.queryForMenu(roleIds, menuAuthorityObjType);
    }
}
