package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;

/**
 * 访问日志Service。
 * @since 1.0
 */
public interface AccessLogService {

    Long create(AccessLogDTO accessLogDTO);

    AccessLogDTO getById(Long id);

    PagingResult<AccessLogDTO> pagingQuery(PagingQuery<AccessLogQueryDTO> pagingQuery);
}
