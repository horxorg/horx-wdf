package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;
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
 * 数据权限Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/dataPermission")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface DataPermissionService {

    @GetMapping("/getById")
    DataPermissionDefDTO getById(@RequestParam Long id);

    @GetMapping("/getByCode")
    DataPermissionDefDTO getByCode(@RequestParam String code);

    @PostMapping("/pagingQuery")
    PagingResult<DataPermissionDefDTO> pagingQuery(@RequestBody PagingQuery<DataPermissionQueryDTO> pagingQuery);

    @GetMapping("/queryForAuthorityObj")
    List<DataPermissionDefDTO> queryForAuthorityObj(@RequestParam String authorityObjType, @RequestParam Long authorityObjId);

    @PostMapping("/create")
    Long create(@RequestBody DataPermissionDefDTO dataPermissionDTO);

    @PostMapping("/modify")
    void modify(@RequestBody DataPermissionDefDTO dataPermissionDTO);

    @PostMapping("/remove")
    void remove(@RequestParam Long[] ids);
}
