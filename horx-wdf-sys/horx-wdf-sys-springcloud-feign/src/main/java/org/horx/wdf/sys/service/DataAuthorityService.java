package org.horx.wdf.sys.service;

import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
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
 * 数据授权Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/dataAuthority")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface DataAuthorityService {

    @PostMapping("/queryRequestDataAuthority")
    List<DataAuthorityDTO> queryRequestDataAuthority(@RequestParam Long userId, @RequestParam Long[] roleIds, @RequestParam Long dataPermissionId);

    @PostMapping("/queryAdminRoleDataAuthority")
    List<DataAuthorityDTO> queryAdminRoleDataAuthority(@RequestParam Long[] roleIds, @RequestParam Long dataPermissionId);

    @GetMapping("/getById")
    DataAuthorityDTO getById(@RequestParam Long id);

    @GetMapping("/getByObj")
    DataAuthorityDTO getByObj(@RequestParam Long dataPermissionId, @RequestParam String objType, @RequestParam Long objId);

    @PostMapping("/create")
    Long create(@RequestBody DataAuthorityDTO dataAuthorityDTO);

    @PostMapping("/modify")
    void modify(@RequestBody DataAuthorityDTO dataAuthorityDTO);

    @PostMapping("/queryDetailByAuthorityIds")
    List<DataAuthorityDetailDTO> queryDetailByAuthorityIds(@RequestParam Long[] authorityIds);

    @PostMapping("/validate")
    boolean validate(@RequestParam Long[] authorityIds, @RequestParam String value);
}
