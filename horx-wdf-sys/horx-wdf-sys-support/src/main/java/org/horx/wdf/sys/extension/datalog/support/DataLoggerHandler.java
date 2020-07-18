package org.horx.wdf.sys.extension.datalog.support;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.extension.datalog.DataLogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * 数据操作日志记录到日志文件的Handler。
 * @since 1.0
 */
public class DataLoggerHandler implements DataLogHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoggerHandler.class);

    @Override
    public void handle(DataOperationLogDTO dataLog) {
        LOGGER.info(JsonUtils.toJson(dataLog, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")));
    }
}
