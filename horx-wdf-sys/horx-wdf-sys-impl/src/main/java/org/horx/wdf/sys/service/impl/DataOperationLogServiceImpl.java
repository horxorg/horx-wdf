package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.converter.DataOperationLogConverter;
import org.horx.wdf.sys.domain.DataOperationLog;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;
import org.horx.wdf.sys.manager.DataOperationLogManager;
import org.horx.wdf.sys.service.DataOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据操作日志Service实现。
 * @since 1.0
 */
@Service("dataOperationLogService")
public class DataOperationLogServiceImpl implements DataOperationLogService {

    @Autowired
    private DataOperationLogManager dataOperationLogManager;

    @Autowired
    private DataOperationLogConverter dataOperationLogConverter;

    @Override
    public Long create(DataOperationLogDTO dataOperationLogDTO) {
        DataOperationLog accessLog = dataOperationLogConverter.fromDto(dataOperationLogDTO);
        return dataOperationLogManager.create(accessLog);
    }

    @Override
    public DataOperationLogDTO getById(Long id) {
        DataOperationLog dataOperationLog = dataOperationLogManager.getById(id);
        DataOperationLogDTO dto = dataOperationLogConverter.toDto(dataOperationLog);
        return dto;
    }

    @Override
    public PaginationResult<DataOperationLogDTO> paginationQuery(PaginationQuery<DataOperationLogQueryDTO> paginationQuery) {
        DataOperationLogQueryDTO dataOperationLogQueryDTO = paginationQuery.getQuery();
        PaginationParam paginationParam = paginationQuery.getPaginationParam();
        PaginationResult<DataOperationLog> paginationResult = dataOperationLogManager.paginationQuery(dataOperationLogQueryDTO, paginationParam);
        PaginationResult<DataOperationLogDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<DataOperationLogDTO> dtoList = dataOperationLogConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
    }
}
