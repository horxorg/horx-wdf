package org.horx.wdf.sys.extension.datalog;

import org.horx.wdf.sys.dto.DataOperationLogDTO;

/**
 * 数据操作日志Handler接口。
 * @since 1.0
 */
public interface DataLogHandler {

    void handle(DataOperationLogDTO dataLog);
}
