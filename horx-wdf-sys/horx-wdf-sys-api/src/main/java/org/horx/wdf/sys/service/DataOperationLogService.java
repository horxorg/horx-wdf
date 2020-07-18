package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;

/**
 * 数据操作日志Service。
 * @since 1.0
 */
public interface DataOperationLogService {

    Long create(DataOperationLogDTO dataOperationLogDTO);

    DataOperationLogDTO getById(Long id);

    PagingResult<DataOperationLogDTO> pagingQuery(PagingQuery<DataOperationLogQueryDTO> pagingQuery);
}
