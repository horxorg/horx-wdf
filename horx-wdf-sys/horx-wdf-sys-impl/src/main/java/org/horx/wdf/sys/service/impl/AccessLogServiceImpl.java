package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
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
    public PagingResult<AccessLogDTO> pagingQuery(PagingQuery<AccessLogQueryDTO> pagingQuery) {
        AccessLogQueryDTO accessLogQuery = pagingQuery.getQuery();
        PagingParam pagingParam = pagingQuery.getPagingParam();
        PagingResult<AccessLog> pagingResult = accessLogManager.pagingQuery(accessLogQuery, pagingParam);
        PagingResult<AccessLogDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<AccessLogDTO> dtoList = accessLogConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }
}
