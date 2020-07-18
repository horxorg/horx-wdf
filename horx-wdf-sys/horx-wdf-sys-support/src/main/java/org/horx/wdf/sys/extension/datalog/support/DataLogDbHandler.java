package org.horx.wdf.sys.extension.datalog.support;

import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.extension.datalog.DataLogHandler;
import org.horx.wdf.sys.service.DataOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据操作日志记录到数据库的Handler。
 * @since 1.0
 */
public class DataLogDbHandler implements DataLogHandler {

    @Autowired
    private DataOperationLogService dataOperationLogService;

    @Override
    public void handle(DataOperationLogDTO dataOperationLogDTO) {
        dataOperationLogService.create(dataOperationLogDTO);
    }
}
