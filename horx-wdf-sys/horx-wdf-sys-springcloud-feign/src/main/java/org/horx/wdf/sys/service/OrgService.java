package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.OrgWithAuthDTO;
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
 * 机构Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/org")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface OrgService {

    @GetMapping("/getById")
    OrgDTO getById(@RequestParam Long id);

    @PostMapping("/getByIdAuthorized")
    OrgDTO getByIdAuthorized(@RequestParam Long id, @RequestBody SysDataAuthDTO sysDataAuth);

    @PostMapping("/query")
    List<OrgDTO> query(@RequestBody PagingQuery<OrgQueryDTO> pagingQuery);

    @PostMapping("/create")
    Long create(@RequestBody OrgWithAuthDTO orgWithAuthDTO);

    @PostMapping("/modify")
    void modify(@RequestBody OrgWithAuthDTO orgWithAuthDTO);

    @PostMapping("/remove")
    void remove(@RequestBody BatchWithSysAuthDTO batchWithAuthDTO);

    @PostMapping("/validate")
    boolean validate(@RequestBody OrgAuthDTO orgAuth, @RequestParam Long orgId);
}
