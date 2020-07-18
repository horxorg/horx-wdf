package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据授权控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/dataAuthority")
@RestController
public class DataAuthorityController {

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @PostMapping("/queryRequestDataAuthority")
    public List<DataAuthorityDTO> queryRequestDataAuthority(@RequestParam Long userId, @RequestParam Long[] roleIds, @RequestParam Long dataPermissionId) {
        return dataAuthorityService.queryRequestDataAuthority(userId, roleIds, dataPermissionId);
    }

    @PostMapping("/queryAdminRoleDataAuthority")
    public List<DataAuthorityDTO> queryAdminRoleDataAuthority(@RequestParam Long[] roleIds, @RequestParam Long dataPermissionId) {
        return dataAuthorityService.queryAdminRoleDataAuthority(roleIds, dataPermissionId);
    }

    @GetMapping("/getById")
    public DataAuthorityDTO getById(@RequestParam Long id) {
        return dataAuthorityService.getById(id);
    }

    @GetMapping("/getByObj")
    public DataAuthorityDTO getByObj(@RequestParam Long dataPermissionId, @RequestParam String objType, @RequestParam Long objId) {
        return dataAuthorityService.getByObj(dataPermissionId, objType, objId);
    }

    @PostMapping("/create")
    public Long create(@RequestBody DataAuthorityDTO dataAuthorityDTO) {
        return dataAuthorityService.create(dataAuthorityDTO);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody DataAuthorityDTO dataAuthorityDTO) {
        dataAuthorityService.modify(dataAuthorityDTO);
    }

    @PostMapping("/queryDetailByAuthorityIds")
    public List<DataAuthorityDetailDTO> queryDetailByAuthorityIds(@RequestParam Long[] authorityIds) {
        return dataAuthorityService.queryDetailByAuthorityIds(authorityIds);
    }

    @PostMapping("/validate")
    public boolean validate(@RequestParam Long[] authorityIds, @RequestParam String value) {
        return dataAuthorityService.validate(authorityIds, value);
    }
}
