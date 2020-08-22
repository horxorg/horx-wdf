package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.domain.DataOperationLog;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;

/**
 * 数据操作日志Manager。
 * @since 1.0
 */
public interface DataOperationLogManager {

    Long create(DataOperationLog dataOperationLog);

    void removeHistory();

    DataOperationLog getById(Long id);

    PaginationResult<DataOperationLog> paginationQuery(DataOperationLogQueryDTO dataOperationLogQuery,
                                                       PaginationParam paginationParam);
}
