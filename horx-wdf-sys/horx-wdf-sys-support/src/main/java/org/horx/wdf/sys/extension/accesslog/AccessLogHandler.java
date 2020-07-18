package org.horx.wdf.sys.extension.accesslog;

import org.horx.wdf.sys.dto.AccessLogDTO;

/**
 * 访问日志Handler。
 * @since 1.0
 */
public interface AccessLogHandler {

    void handle(AccessLogDTO accessLog);
}
