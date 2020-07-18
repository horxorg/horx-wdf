package org.horx.wdf.sys.extension.accesslog.support;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.extension.accesslog.AccessLogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * 访问日志记录到日志文件的Handler。
 * @since 1.0
 */
public class AccessLoggerHandler implements AccessLogHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLoggerHandler.class);

    @Override
    public void handle(AccessLogDTO accessLog) {
        LOGGER.info(JsonUtils.toJson(accessLog, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")));
    }
}
