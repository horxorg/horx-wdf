package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingResult;
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

    PagingResult<AccessLog> pagingQuery(AccessLogQueryDTO accessLogQuery, PagingParam pagingParam);
}
