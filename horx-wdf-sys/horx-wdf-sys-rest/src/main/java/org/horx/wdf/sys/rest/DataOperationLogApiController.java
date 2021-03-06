package org.horx.wdf.sys.rest;

import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.converter.DataOperationLogVoConverter;
import org.horx.wdf.sys.converter.query.DataOperationLogQueryVoConverter;
import org.horx.wdf.sys.vo.DataOperationLogVO;
import org.horx.wdf.sys.vo.query.DataOperationLogQueryVO;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;
import org.horx.wdf.sys.service.DataOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据操作日志API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/dataLog")
public class DataOperationLogApiController {

    @Autowired
    private DataOperationLogService dataOperationLogService;

    @Autowired
    private DataOperationLogVoConverter dataOperationLogVoConverter;

    @Autowired
    private DataOperationLogQueryVoConverter dataOperationLogQueryVoConverter;

    @AccessPermission("sys.dataLog.query")
    @GetMapping("/{id}")
    public Result<DataOperationLogVO> getById(@PathVariable("id") Long id) {
        DataOperationLogDTO dto = dataOperationLogService.getById(id);
        DataOperationLogVO vo = dataOperationLogVoConverter.toVo(dto);
        return new Result<>(vo);
    }

    @AccessPermission("sys.dataLog.query")
    @PostMapping("/paginationQuery")
    public PaginationResult<DataOperationLogVO> paginationQuery(@ArgEntity DataOperationLogQueryVO query, PaginationParam paginationParam) {
        DataOperationLogQueryDTO dataOperationLogQueryDTO = dataOperationLogQueryVoConverter.fromVo(query);
        PaginationQuery<DataOperationLogQueryDTO> paginationQuery = new PaginationQuery<>(dataOperationLogQueryDTO, paginationParam);
        PaginationResult<DataOperationLogDTO> paginationResult = dataOperationLogService.paginationQuery(paginationQuery);
        PaginationResult<DataOperationLogVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(dataOperationLogVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }
}
