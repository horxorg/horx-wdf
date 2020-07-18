package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
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
    public PagingResult<DataOperationLogDTO> pagingQuery(PagingQuery<DataOperationLogQueryDTO> pagingQuery) {
        DataOperationLogQueryDTO dataOperationLogQueryDTO = pagingQuery.getQuery();
        PagingParam pagingParam = pagingQuery.getPagingParam();
        PagingResult<DataOperationLog> pagingResult = dataOperationLogManager.pagingQuery(dataOperationLogQueryDTO, pagingParam);
        PagingResult<DataOperationLogDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<DataOperationLogDTO> dtoList = dataOperationLogConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }
}
