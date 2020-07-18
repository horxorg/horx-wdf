package org.horx.wdf.sys.extension.accesslog.support;

import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.extension.accesslog.AccessLogHandler;
import org.horx.wdf.sys.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 访问日志记录到数据库的Handler。
 * @since 1.0
 */
public class AccessLogDbHandler implements AccessLogHandler {

    @Autowired
    private AccessLogService accessLogService;

    @Override
    public void handle(AccessLogDTO accessLog) {
        accessLogService.create(accessLog);
    }
}
