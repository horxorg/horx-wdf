package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.domain.AccessLog;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;

/**
 * 访问日志Manager。
 * @since 1.0
 */
public interface AccessLogManager {

    Long create(AccessLog accessLog);

    void removeHistory();

    AccessLog getById(Long id);

    PaginationResult<AccessLog> paginationQuery(AccessLogQueryDTO accessLogQuery, PaginationParam paginationParam);
}
