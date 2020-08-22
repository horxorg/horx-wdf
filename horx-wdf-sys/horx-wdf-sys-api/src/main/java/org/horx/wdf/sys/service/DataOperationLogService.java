package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;

/**
 * 数据操作日志Service。
 * @since 1.0
 */
public interface DataOperationLogService {

    Long create(DataOperationLogDTO dataOperationLogDTO);

    DataOperationLogDTO getById(Long id);

    PaginationResult<DataOperationLogDTO> paginationQuery(PaginationQuery<DataOperationLogQueryDTO> paginationQuery);
}
