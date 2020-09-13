package org.horx.wdf.common.spring.session;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.extension.session.SessionAttrDTO;
import org.horx.wdf.common.extension.session.SessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.session.Session;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * SpringSession的实现。
 * @since 1.0
 */
public class SpringSession implements Session {
    private static final Logger logger = LoggerFactory.getLogger(SpringSession.class);

    private CacheableJdbcSessionRepository sessionRepository;

    private long lastPersistTime;
    private Long persistId;
    private Long userId;

    private String id;
    private long creationTime;
    private long lastAccessedTime;
    private long maxInactiveInterval;
    private long cacheTime = System.currentTimeMillis();

    public SpringSession(CacheableJdbcSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;

        this.id = UUID.randomUUID().toString();
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = this.creationTime;
        this.maxInactiveInterval = TimeUnit.MILLISECONDS.toSeconds(
                sessionRepository.getDefaultMaxInactiveInterval().toMillis());

        persist(false);
    }

    public SpringSession(CacheableJdbcSessionRepository sessionRepository, SessionDTO session) {
        this.sessionRepository = sessionRepository;

        this.id = session.getSessionKey();
        if (session.getCreateTime() != null) {
            this.creationTime = session.getCreateTime().getTime();
        }
        if (session.getLastAccessTime() != null) {
            this.lastAccessedTime = session.getLastAccessTime().getTime();
        }
        if (session.getInactiveInterval() != null) {
            this.maxInactiveInterval = session.getInactiveInterval();
        } else {
            this.maxInactiveInterval = TimeUnit.MILLISECONDS.toSeconds(
                    sessionRepository.getDefaultMaxInactiveInterval().toMillis());
        }
        this.persistId = session.getId();
        this.userId = session.getUserId();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String changeSessionId() {
        String newSessionKey = UUID.randomUUID().toString();
        SessionDTO session = new SessionDTO();
        session.setId(persistId);
        session.setSessionKey(newSessionKey);
        sessionRepository.getSessionService().modify(session);

        if (sessionRepository.isEnableRedis()) {
            String oldKey = sessionRepository.getRedisKey(id);
            String newKey = sessionRepository.getRedisKey(newSessionKey);
            Map attrs = sessionRepository.getRedisTemplate().opsForHash().entries(oldKey);
            sessionRepository.getRedisTemplate().opsForHash().putAll(
                    sessionRepository.getRedisKey(newSessionKey), attrs);
            sessionRepository.getRedisTemplate().delete(oldKey);
            sessionRepository.getRedisTemplate().expire(newKey, maxInactiveInterval, TimeUnit.SECONDS);
        }

        sessionRepository.changeSessionId(id, newSessionKey);
        id = newSessionKey;
        return newSessionKey;
    }

    @Override
    public <T> T getAttribute(String key) {
        if (sessionRepository.getSysConfig().getUserIdSessionKey().equals(key)) {
            return (T)userId;
        }

        if (sessionRepository.isEnableRedis()) {
            Object valueObj = sessionRepository.getRedisTemplate().opsForHash().get(
                    sessionRepository.getRedisKey(id), sessionRepository.getRedisAttrKey(key));
            if (valueObj == null) {
                return null;
            }
            Map<String, String> attrMap = JsonUtils.fromJson(valueObj.toString(), Map.class);
            return (T)attrValueToObject(attrMap.get("type"), attrMap.get("value"));
        }

        SessionAttrDTO attrDTO = sessionRepository.getSessionService().getAttrByKey(persistId, key);
        return (attrDTO == null) ? null : (T)attrValueToObject(attrDTO.getAttrType(), attrDTO.getAttrValue());
    }

    @Override
    public <T> T getRequiredAttribute(String name) {
        return getAttribute(name);
    }

    @Override
    public <T> T getAttributeOrDefault(String name, T defaultValue) {
        T value = getAttribute(name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    @Override
    public Set<String> getAttributeNames() {
        if (sessionRepository.isEnableRedis()) {
            return sessionRepository.getRedisTemplate().opsForHash().keys(sessionRepository.getRedisKey(id));
        }

        List<SessionAttrDTO> list = sessionRepository.getSessionService().queryAttrBySessionId(persistId);
        Set<String> result = new HashSet<>();
        String keyPrefix = CacheableJdbcSessionRepository.SESSION_ATTR_REDIS_KEY_PREFIX;
        for (SessionAttrDTO attrDTO : list) {
            if (attrDTO.getAttrKey().startsWith(keyPrefix)) {
                result.add(attrDTO.getAttrKey().substring(keyPrefix.length() + 1));
            }
        }
        return result;
    }

    @Override
    public void setAttribute(String key, Object value) {
        if (value == null) {
            removeAttribute(key);
            return;
        }

        try {
            lastAccessedTime = System.currentTimeMillis();

            if (sessionRepository.getSysConfig().getUserIdSessionKey().equals(key)) {
                userId = (Long) value;
                creationTime = System.currentTimeMillis();
                persist(true);
                return;
            } else if (!sessionRepository.getSysConfig().isSessionUseAttr()) {
                throw new RuntimeException("不支持session attr，如果需要支持，" +
                        "请设置common.properties中common.session.useAttr=true");
            }

            if (sessionRepository.isEnableRedis()) {
                Map<String, String> attrMap = new HashMap<>();
                attrMap.put("type", value.getClass().getName());
                attrMap.put("value", attrValueToString(value));
                sessionRepository.getRedisTemplate().opsForHash().put(sessionRepository.getRedisKey(id),
                        sessionRepository.getRedisAttrKey(key), JsonUtils.toJson(attrMap));
                return;
            }

            SessionAttrDTO attrDTO = sessionRepository.getSessionService().getAttrByKey(persistId, key);
            boolean exists = attrDTO != null;

            SessionAttrDTO sessionAttr = new SessionAttrDTO();
            sessionAttr.setSessionId(persistId);
            sessionAttr.setAttrKey(key);
            sessionAttr.setAttrType(value.getClass().getName());
            sessionAttr.setAttrValue(attrValueToString(value));
            if (exists) {
                sessionRepository.getSessionService().modifyAttr(sessionAttr);
            } else {
                try {
                    sessionRepository.getSessionService().createAttr(sessionAttr);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    sessionRepository.getSessionService().modifyAttr(sessionAttr);
                }
            }
        } catch (Exception e) {
            logger.error("设置session属性异常,session:{},attr:", id, key, e);
            throw e;
        }
    }

    @Override
    public void removeAttribute(String key) {
        try {
            lastAccessedTime = System.currentTimeMillis();

            if (sessionRepository.getSysConfig().getUserIdSessionKey().equals(key)) {
                sessionRepository.deleteById(id);
            } else if (sessionRepository.getSysConfig().isSessionUseAttr()) {
                if (sessionRepository.isEnableRedis()) {
                    sessionRepository.getRedisTemplate().opsForHash().delete(sessionRepository.getRedisKey(id),
                            sessionRepository.getRedisAttrKey(key));
                } else {
                    sessionRepository.getSessionService().removeAttrByKey(persistId, key);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Instant getCreationTime() {
        return Instant.ofEpochMilli(creationTime);
    }

    @Override
    public void setLastAccessedTime(Instant lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime.toEpochMilli();
        try {
            if (needPersist()) {
                updateLastAccessedTime();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Instant getLastAccessedTime() {
        return Instant.ofEpochMilli(lastAccessedTime);
    }

    @Override
    public void setMaxInactiveInterval(Duration maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval.getSeconds();
        persist(false);
    }

    @Override
    public Duration getMaxInactiveInterval() {
        return Duration.ofSeconds(maxInactiveInterval);
    }

    @Override
    public boolean isExpired() {
        if (this.maxInactiveInterval < 0) {
            return false;
        } else {
            return System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(this.maxInactiveInterval)
                    >= this.lastAccessedTime;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Session && this.id.equals(((Session)obj).getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    long getCacheTime() {
        return cacheTime;
    }

    boolean needPersist() {
        if (lastPersistTime == 0L ||
                lastAccessedTime >= lastPersistTime + sessionRepository.getPersistInterval().toMillis() ||
                System.currentTimeMillis() >= lastPersistTime + sessionRepository.getPersistInterval().toMillis()) {
            return true;
        }
        return false;
    }

    void refresh(SpringSession springSession) {
        if (springSession == null) {
            return;
        }

        this.setLastAccessedTime(springSession.getLastAccessedTime());
        this.setMaxInactiveInterval(springSession.getMaxInactiveInterval());
    }

    /**
     * 对session对象持久化保存。
     */
    void persist(boolean isLogin) {
        SessionDTO session = new SessionDTO();
        if (persistId == null) {
            session.setSessionKey(id);

            Date creationTimeDate = new Date();
            creationTimeDate.setTime(creationTime);
            session.setCreateTime(creationTimeDate);
            session.setLastAccessTime(creationTimeDate);

            session.setInactiveInterval((int)maxInactiveInterval);
            session.setExpiredTime(getExpiredTime(creationTime));

            RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) reqAttrs).getRequest();
            session.setClientIp(sessionRepository.getWebTool().getClientIp(request));

            if (isLogin && userId != null) {
                session.setUserId(userId);
            }
        } else {
            session.setId(persistId);

            Date lastAccessedTimeDate = new Date();
            lastAccessedTimeDate.setTime(lastAccessedTime);
            session.setLastAccessTime(lastAccessedTimeDate);
            session.setInactiveInterval((int)maxInactiveInterval);
            session.setExpiredTime(getExpiredTime(lastAccessedTime));

            if (isLogin && userId != null) {
                session.setUserId(userId);

                Date creationTimeDate = new Date();
                creationTimeDate.setTime(creationTime);
                session.setCreateTime(creationTimeDate);

                RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = ((ServletRequestAttributes) reqAttrs).getRequest();
                session.setClientIp(sessionRepository.getWebTool().getClientIp(request));
            }
        }

        lastPersistTime = System.currentTimeMillis();
        if (persistId == null) {
            persistId = sessionRepository.getSessionService().create(session);
        } else {
            sessionRepository.getSessionService().modify(session);
        }

        if (sessionRepository.isEnableRedis()) {
            Map<String, Object> map = new HashMap<>();
            if (persistId != null) {
                map.put("persistId", String.valueOf(persistId));
            }
            if (userId != null) {
                map.put("userId", String.valueOf(userId));
            }
            map.put("maxInactiveInterval", String.valueOf(maxInactiveInterval));

            String redisKey = sessionRepository.getRedisKey(id);
            sessionRepository.getRedisTemplate().opsForHash().putAll(redisKey, map);
            sessionRepository.getRedisTemplate().expire(redisKey, maxInactiveInterval, TimeUnit.SECONDS);
        }
    }

    void updateLastAccessedTime() {
        if (sessionRepository.isEnableRedis()) {
            sessionRepository.getRedisTemplate().expire(sessionRepository.getRedisKey(id),
                    maxInactiveInterval, TimeUnit.SECONDS);
        }

        if (persistId != null) {
            SessionDTO session = new SessionDTO();
            session.setId(persistId);
            Date lastAccessedTimeDate = new Date();
            lastAccessedTimeDate.setTime(lastAccessedTime);
            session.setLastAccessTime(lastAccessedTimeDate);
            session.setExpiredTime(getExpiredTime(lastAccessedTime));
            sessionRepository.getSessionService().modify(session);
        }

    }

    private Date getExpiredTime(long time) {
        Date date = new Date();
        date.setTime(time + TimeUnit.SECONDS.toMillis(this.maxInactiveInterval));
        return date;
    }

    private String attrValueToString(Object value) {
        if (value.getClass().isPrimitive()) {
            return value.toString();
        } else if (value instanceof String) {
            return (String)value;
        }

        return JsonUtils.toJson(value);
    }

    private Object attrValueToObject(String type, String value) {
        try {
            Class cls = Class.forName(type);
            Object obj;
            if (cls.isPrimitive() || cls.isAssignableFrom(String.class)) {
                SimpleTypeConverter typeConverter = new SimpleTypeConverter();
                obj = typeConverter.convertIfNecessary(value, cls);
            } else {
                obj = JsonUtils.fromJson(value, cls);
            }

            return obj;
        } catch (Exception e) {
            throw new RuntimeException("get session attr error, type:" + type + ", value:" + value, e);
        }
    }

}
