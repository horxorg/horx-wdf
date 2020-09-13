package org.horx.wdf.springboot.config;

import org.horx.wdf.sys.manager.AccessLogManager;
import org.horx.wdf.sys.manager.DataOperationLogManager;
import org.horx.wdf.sys.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    private AccessLogManager accessLogManager;

    @Autowired
    private DataOperationLogManager dataOperationLogManager;

    @Autowired
    @Qualifier("sessionService")
    private SessionService sessionService;

    @Scheduled(initialDelay = 30000L, fixedDelay = 86400000L)
    public void scheduledAccessLog() {
        accessLogManager.removeHistory();
    }

    @Scheduled(initialDelay = 60000L, fixedDelay = 86400000L)
    public void scheduledDataLog() {
        dataOperationLogManager.removeHistory();
    }

    @Scheduled(initialDelay = 40000L, fixedDelay = 300000L)
    public void scheduleSessionCleaner() {
        sessionService.removeExpired();
    }
}
