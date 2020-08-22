package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;
import org.horx.wdf.sys.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问日志控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/accessLog")
@RestController
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @PostMapping("/create")
    public Long create(@RequestBody AccessLogDTO accessLogDTO) {
        return accessLogService.create(accessLogDTO);
    }

    @GetMapping("/getById")
    public AccessLogDTO getById(@RequestParam Long id) {
        return accessLogService.getById(id);
    }

    @PostMapping("/paginationQuery")
    public PaginationResult<AccessLogDTO> paginationQuery(@RequestBody PaginationQuery<AccessLogQueryDTO> paginationQuery) {
        return accessLogService.paginationQuery(paginationQuery);
    }
}
