package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;
import org.horx.wdf.sys.service.DataOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据操作日志控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/dataOperationLog")
@RestController
public class DataOperationLogController {

    @Autowired
    private DataOperationLogService dataOperationLogService;

    @PostMapping("/create")
    public Long create(@RequestBody DataOperationLogDTO dataOperationLogDTO) {
        return dataOperationLogService.create(dataOperationLogDTO);
    }

    @GetMapping("/getById")
    public DataOperationLogDTO getById(@RequestParam Long id) {
        return dataOperationLogService.getById(id);
    }

    @PostMapping("/paginationQuery")
    public PaginationResult<DataOperationLogDTO> paginationQuery(@RequestBody PaginationQuery<DataOperationLogQueryDTO> paginationQuery) {
        return dataOperationLogService.paginationQuery(paginationQuery);
    }
}
