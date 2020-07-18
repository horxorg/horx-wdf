package org.horx.wdf.springcloud.provider.config;

import org.horx.wdf.sys.manager.AccessLogManager;
import org.horx.wdf.sys.manager.DataOperationLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    private AccessLogManager accessLogManager;

    @Autowired
    private DataOperationLogManager dataOperationLogManager;

    @Scheduled(initialDelay = 30000L, fixedDelay = 86400000L)
    public void scheduledAccessLog() {
        accessLogManager.removeHistory();
    }

    @Scheduled(initialDelay = 60000L, fixedDelay = 86400000L)
    public void scheduledDataLog() {
        dataOperationLogManager.removeHistory();
    }
}
