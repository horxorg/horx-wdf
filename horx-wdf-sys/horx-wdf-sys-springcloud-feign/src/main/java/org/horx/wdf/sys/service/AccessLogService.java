package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;
import org.horx.wdf.sys.springcloud.SpringcloudConsumerContextInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 访问日志Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/accessLog")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface AccessLogService {

    @PostMapping("/create")
    Long create(@RequestBody AccessLogDTO accessLogDTO);

    @GetMapping("/getById")
    AccessLogDTO getById(@RequestParam Long id);

    @PostMapping("/pagingQuery")
    PagingResult<AccessLogDTO> pagingQuery(@RequestBody PagingQuery<AccessLogQueryDTO> pagingQuery);
}
