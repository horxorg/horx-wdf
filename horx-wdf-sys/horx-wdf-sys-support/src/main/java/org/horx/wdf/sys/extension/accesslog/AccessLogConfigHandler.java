package org.horx.wdf.sys.extension.accesslog;

import org.horx.wdf.sys.dto.AccessLogDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 访问日志配置Handler。
 * @since 1.0
 */
public class AccessLogConfigHandler implements AccessLogHandler {

    private List<AccessLogHandler> accessLogHandlerList;

    public List<AccessLogHandler> getAccessLogHandlerList() {
        return accessLogHandlerList;
    }

    public void setAccessLogHandlerList(List<AccessLogHandler> accessLogHandlerList) {
        this.accessLogHandlerList = accessLogHandlerList;
    }

    @Override
    public void handle(AccessLogDTO accessLog) {
        if (CollectionUtils.isEmpty(accessLogHandlerList)) {
            return;
        }

        for (AccessLogHandler accessLogHandler : accessLogHandlerList) {
            accessLogHandler.handle(accessLog);
        }
    }
}
