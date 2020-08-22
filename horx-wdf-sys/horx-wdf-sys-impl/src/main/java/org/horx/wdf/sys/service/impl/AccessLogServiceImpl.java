package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.converter.AccessLogConverter;
import org.horx.wdf.sys.domain.AccessLog;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;
import org.horx.wdf.sys.manager.AccessLogManager;
import org.horx.wdf.sys.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 访问日志Service实现。
 * @since 1.0
 */
@Service("accessLogService")
public class AccessLogServiceImpl implements AccessLogService {

    @Autowired
    private AccessLogManager accessLogManager;

    @Autowired
    private AccessLogConverter accessLogConverter;

    @Override
    public Long create(AccessLogDTO accessLogDTO) {
        AccessLog accessLog = accessLogConverter.fromDto(accessLogDTO);
        return accessLogManager.create(accessLog);
    }

    @Override
    public AccessLogDTO getById(Long id) {
        AccessLog accessLog = accessLogManager.getById(id);
        AccessLogDTO dto = accessLogConverter.toDto(accessLog);
        return dto;
    }

    @Override
    public PaginationResult<AccessLogDTO> paginationQuery(PaginationQuery<AccessLogQueryDTO> paginationQuery) {
        AccessLogQueryDTO accessLogQuery = paginationQuery.getQuery();
        PaginationParam paginationParam = paginationQuery.getPaginationParam();
        PaginationResult<AccessLog> paginationResult = accessLogManager.paginationQuery(accessLogQuery, paginationParam);
        PaginationResult<AccessLogDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<AccessLogDTO> dtoList = accessLogConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
    }
}
