package org.horx.wdf.common.spring.session;

import org.horx.wdf.common.config.CommonConfig;
import org.horx.wdf.common.extension.session.CommonSessionService;
import org.horx.wdf.common.extension.session.SessionDTO;
import org.horx.wdf.common.tools.WebTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可存储到数据库的SpringSession。
 * @since 1.0
 */
public class CacheableJdbcSessionRepository implements SessionRepository<SpringSession> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheableJdbcSessionRepository.class);

    @Autowired
    private CommonSessionService sessionService;

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private WebTool webTool;

    private Map<String, SpringSession> sessionMap;

    /**
     * session有效期（秒）。
     */
    private int defaultMaxInactiveInterval = 2400;

    /**
     * 持久化间隔（秒）。
     */
    private int persistInterval = 60;



    public CacheableJdbcSessionRepository() {
        sessionMap = new ConcurrentHashMap<>();

        Timer timer = new Timer();
        timer.schedule(new SessionRepositoryTimerTask(), 120000, 120000);
    }

    @Override
    public SpringSession createSession() {
        SpringSession session = new SpringSession(this);
        sessionMap.put(session.getId(), session);
        session.setMaxInactiveIntervalInSeconds(defaultMaxInactiveInterval);
        return session;
    }

    @Override
    public SpringSession getSession(String id) {
        SpringSession springSession = sessionMap.get(id);
        if (springSession == null) {
            SessionDTO session =  sessionService.getBySessionKey(id);
            if (session != null) {
                springSession = new SpringSession(this, session);
                sessionMap.put(springSession.getId(), springSession);
            }
        } else if (springSession.isExpired()) {
            delete(id);
            springSession = null;
        } else {
            springSession.setLastAccessedTime(System.currentTimeMillis());
        }
        return springSession;
    }

    @Override
    public void save(SpringSession session) {
        if (session == null) {
            return;
        }

        if (sessionMap.containsKey(session.getId())) {
            SpringSession oldSession = sessionMap.get(session.getId());
            oldSession.refresh(session);
            if (oldSession.isExpired()) {
                delete(oldSession.getId());
            }
        }
    }

    @Override
    public void delete(String id) {
        sessionService.removeBySessionKey(id);
        sessionMap.remove(id);
    }

    /**
     * 获取session有效期（秒）。
     * @return
     */
    public int getDefaultMaxInactiveInterval() {
        return defaultMaxInactiveInterval;
    }

    /**
     * 设置session有效期（秒）。
     * @param defaultMaxInactiveInterval session有效期（秒）。
     */
    public void setDefaultMaxInactiveInterval(int defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    public int getPersistInterval() {
        return persistInterval;
    }

    public void setPersistInterval(int persistInterval) {
        this.persistInterval = persistInterval;
    }

    CommonSessionService getSessionService() {
        return sessionService;
    }

    CommonConfig getSysConfig() {
        return commonConfig;
    }

    WebTool getWebTool() {
        return webTool;
    }

    class SessionRepositoryTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                for (Map.Entry<String, SpringSession> entry : sessionMap.entrySet()) {
                    if (entry.getValue().isExpired()) {

                        delete(entry.getKey());
                    } else if (entry.getValue().needPersist()) {
                        entry.getValue().persist(false);
                    }

                }

                sessionService.removeExpired();
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }
}
