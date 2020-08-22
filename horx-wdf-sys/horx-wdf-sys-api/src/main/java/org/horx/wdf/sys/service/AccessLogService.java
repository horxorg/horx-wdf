package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;

/**
 * 访问日志Service。
 * @since 1.0
 */
public interface AccessLogService {

    Long create(AccessLogDTO accessLogDTO);

    AccessLogDTO getById(Long id);

    PaginationResult<AccessLogDTO> paginationQuery(PaginationQuery<AccessLogQueryDTO> paginationQuery);
}
