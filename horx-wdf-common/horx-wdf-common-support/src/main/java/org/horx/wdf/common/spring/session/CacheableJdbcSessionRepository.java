package org.horx.wdf.common.spring.session;

import org.horx.common.utils.DurationUtils;
import org.horx.wdf.common.config.CommonConfig;
import org.horx.wdf.common.extension.session.CommonSessionService;
import org.horx.wdf.common.extension.session.SessionDTO;
import org.horx.wdf.common.tools.WebTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.SessionRepository;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 可存储到数据库的SpringSession。
 * @since 1.0
 */
public class CacheableJdbcSessionRepository implements SessionRepository<SpringSession> {
    private static final Logger logger = LoggerFactory.getLogger(CacheableJdbcSessionRepository.class);

    private static final Collection<String> ATTR_KEY = CollectionUtils.arrayToList(new String[]{"persistId",
            "userId", "maxInactiveInterval"});
    private static final String SESSION_REDIS_KEY_PREFIX = "session.";
    static final String SESSION_ATTR_REDIS_KEY_PREFIX = "attr_";

    @Autowired
    private CommonSessionService sessionService;

    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private WebTool webTool;

    private RedisTemplate redisTemplate;

    private Map<String, SpringSession> sessionMap;

    /**
     * session有效期。
     */
    private Duration defaultMaxInactiveInterval = Duration.ofMinutes(40);

    /**
     * 持久化间隔。
     */
    private Duration persistInterval = Duration.ofSeconds(60);

    /**
     * 是否启用redis存储。
     */
    private boolean enableRedis;

    /**
     * 本地缓存间隔。
     * 小于0表示不使用本地缓存，等于0表示本地缓存与session生命期一致。
     */
    private Duration localCacheInterval = Duration.ofMinutes(5);

    public CacheableJdbcSessionRepository() {
        sessionMap = new ConcurrentHashMap<>();

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleWithFixedDelay(new SessionRepositoryTimerTask(), 60,
                60, TimeUnit.SECONDS);
    }

    @Override
    public SpringSession createSession() {
        SpringSession session = new SpringSession(this);
        sessionMap.put(session.getId(), session);
        return session;
    }

    @Override
    public SpringSession findById(String id) {
        SpringSession springSession = sessionMap.get(id);
        if (springSession == null) {
            SessionDTO session;
            if (enableRedis) {
                List list = redisTemplate.opsForHash().multiGet(getRedisKey(id), ATTR_KEY);
                session = new SessionDTO();
                if (list.get(0) == null) {
                    return null;
                }
                session.setId(Long.valueOf(String.valueOf(list.get(0))));
                if (list.get(1) != null) {
                    session.setUserId(Long.valueOf(String.valueOf(list.get(1))));
                }
                if (list.get(2) != null) {
                    session.setInactiveInterval(Integer.valueOf(String.valueOf(list.get(2))));
                }
                session.setSessionKey(id);
            } else {
                session =  sessionService.getBySessionKey(id);
            }
            if (session != null) {
                springSession = new SpringSession(this, session);
                sessionMap.put(springSession.getId(), springSession);
            }
        } else if (springSession.isExpired()) {
            deleteById(id);
            springSession = null;
        } else {
            springSession.setLastAccessedTime(Instant.now());
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
                deleteById(oldSession.getId());
            }
        }
    }

    @Override
    public void deleteById(String id) {
        sessionService.removeBySessionKey(id);
        if (enableRedis) {
            redisTemplate.delete(getRedisKey(id));
        }
        sessionMap.remove(id);
    }

    /**
     * 获取session有效期。
     * @return
     */
    public Duration getDefaultMaxInactiveInterval() {
        return defaultMaxInactiveInterval;
    }

    /**
     * 设置session有效期。
     * @param defaultMaxInactiveIntervalDuration session有效期。
     */
    public void setDefaultMaxInactiveInterval(String defaultMaxInactiveIntervalDuration) {
        Duration duration = DurationUtils.parse(defaultMaxInactiveIntervalDuration);
        long minutes = duration.toMinutes();
        if (minutes <= 1 || minutes > 60 * 24 * 2) {
            throw new RuntimeException("defaultMaxInactiveInterval设置非法，请设置1分钟至2天之间的秒数");
        }
        this.defaultMaxInactiveInterval = duration;
    }

    public Duration getPersistInterval() {
        return persistInterval;
    }

    public void setPersistInterval(String persistIntervalDuration) {
        this.persistInterval = DurationUtils.parse(persistIntervalDuration);
    }

    void changeSessionId(String oldId, String newId) {
        SpringSession springSession = sessionMap.get(oldId);
        sessionMap.put(newId, springSession);
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

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isEnableRedis() {
        return enableRedis;
    }

    public void setEnableRedis(boolean enableRedis) {
        this.enableRedis = enableRedis;
    }

    public Duration getLocalCacheInterval() {
        return localCacheInterval;
    }

    public void setLocalCacheInterval(String localCacheIntervalDuration) {
        this.localCacheInterval = DurationUtils.parse(localCacheIntervalDuration);
    }

    String getRedisKey(String sessionKey) {
        return SESSION_REDIS_KEY_PREFIX + sessionKey;
    }

    String getRedisAttrKey(String attrKey) {
        return SESSION_ATTR_REDIS_KEY_PREFIX + attrKey;
    }

    class SessionRepositoryTimerTask implements Runnable {
        @Override
        public void run() {
            try {
                for (Map.Entry<String, SpringSession> entry : sessionMap.entrySet()) {
                    SpringSession springSession = entry.getValue();
                    if (springSession.needPersist()) {
                        springSession.updateLastAccessedTime();
                    }
                    if (System.currentTimeMillis() - springSession.getCacheTime() >= localCacheInterval.toMillis()) {
                        sessionMap.remove(entry.getKey());
                    }
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }
}
