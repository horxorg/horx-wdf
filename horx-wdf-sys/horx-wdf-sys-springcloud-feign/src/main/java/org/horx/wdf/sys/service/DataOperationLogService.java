package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;
import org.horx.wdf.sys.springcloud.SpringcloudConsumerContextInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数据操作日志Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/dataOperationLog")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface DataOperationLogService {

    @PostMapping("/create")
    Long create(@RequestBody DataOperationLogDTO dataOperationLogDTO);

    @GetMapping("/getById")
    DataOperationLogDTO getById(@RequestParam Long id);

    @PostMapping("/pagingQuery")
    PagingResult<DataOperationLogDTO> pagingQuery(@RequestBody PagingQuery<DataOperationLogQueryDTO> pagingQuery);
}
