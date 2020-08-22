package org.horx.wdf.common.spring.session;

import org.horx.common.utils.DateUtils;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSession.class);

    private CacheableJdbcSessionRepository sessionRepository;

    private long lastPersistTime;
    private Long persistId;
    private Long userId;

    private String id;
    private Map<String, Object> sessionAttrs;
    private long creationTime;
    private long lastAccessedTime;
    private long maxInactiveInterval;

    public SpringSession(CacheableJdbcSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;

        this.id = UUID.randomUUID().toString();

        if (sessionRepository.getSysConfig().isSessionUseAttr()) {
            this.sessionAttrs = new HashMap();
        }

        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = this.creationTime;
        this.maxInactiveInterval = 2400;

        persist(false);
    }

    public SpringSession(CacheableJdbcSessionRepository sessionRepository, SessionDTO session) {
        this.sessionRepository = sessionRepository;

        this.id = session.getSessionKey();
        this.creationTime = session.getCreateTime().getTime();
        this.lastAccessedTime = session.getLastAccessTime().getTime();
        this.maxInactiveInterval = session.getInactiveInterval();
        this.persistId = session.getId();
        this.userId = session.getUserId();

        if (sessionRepository.getSysConfig().isSessionUseAttr()) {
            this.sessionAttrs = new HashMap();

            sessionAttrs.put(sessionRepository.getSysConfig().getUserIdSessionKey(), session.getUserId());

            List<SessionAttrDTO> list = sessionRepository.getSessionService().queryAttrBySessionId(persistId);
            for (SessionAttrDTO attr : list) {
                sessionAttrs.put(attr.getAttrKey(), attrValueToObject(attr.getAttrType(), attr.getAttrValue()));
            }
        }
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
        sessionRepository.changeSessionId(id, newSessionKey);
        id = newSessionKey;
        return newSessionKey;
    }

    @Override
    public <T> T getAttribute(String key) {
        if (sessionRepository.getSysConfig().getUserIdSessionKey().equals(key)) {
            return (T)userId;
        }

        if (sessionAttrs == null) {
            return null;
        }
        Object obj = sessionAttrs.get(key);
        return (obj == null) ? null : (T)obj;
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
        if (sessionAttrs == null) {
            return null;
        }
        return new HashSet(this.sessionAttrs.keySet());
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
                userId = (Long)value;
                creationTime = System.currentTimeMillis();
                persist(true);
                return;
            } else if (!sessionRepository.getSysConfig().isSessionUseAttr()) {
                throw new RuntimeException("不支持session attr，如果需要支持，请设置common.properties中common.session.useAttr=true");
            }

            boolean exists = sessionAttrs.containsKey(key);
            sessionAttrs.put(key, value);

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
                    LOGGER.error(e.getMessage(), e);
                    sessionRepository.getSessionService().modifyAttr(sessionAttr);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void removeAttribute(String key) {
        try {
            sessionAttrs.remove(key);
            lastAccessedTime = System.currentTimeMillis();

            if (sessionRepository.getSysConfig().getUserIdSessionKey().equals(key)) {
                userId = null;
                persist(false);
            } else if (sessionRepository.getSysConfig().isSessionUseAttr()) {
                sessionRepository.getSessionService().removeAttrByKey(persistId, key);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
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
                persist(false);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
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
            return System.currentTimeMillis() - TimeUnit.SECONDS.toMillis((long)this.maxInactiveInterval) >= this.lastAccessedTime;
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

    public boolean needPersist() {
        if (lastPersistTime == 0L || lastAccessedTime >= lastPersistTime + TimeUnit.SECONDS.toMillis((long)sessionRepository.getPersistInterval()) ||
            System.currentTimeMillis() >= lastPersistTime + TimeUnit.SECONDS.toMillis((long)sessionRepository.getPersistInterval())) {
            return true;
        }
        return false;
    }

    public void refresh(SpringSession springSession) {
        if (springSession == null) {
            return;
        }

        this.setLastAccessedTime(springSession.getLastAccessedTime());
        this.setMaxInactiveInterval(springSession.getMaxInactiveInterval());

        if (sessionRepository.getSysConfig().isSessionUseAttr()) {
            for (Map.Entry<String, Object> entry : springSession.sessionAttrs.entrySet()) {
                if (this.sessionAttrs.containsKey(entry.getKey())) {
                    Object oldV = this.sessionAttrs.get(entry.getKey());
                    Object newV = springSession.sessionAttrs.get(entry.getKey());
                    if (oldV == null && newV != null || oldV != null && !oldV.equals(newV)) {
                        this.sessionAttrs.put(entry.getKey(), newV);
                    }
                } else {
                    this.sessionAttrs.put(entry.getKey(), springSession.sessionAttrs.get(entry.getKey()));
                }
            }
        }
    }

    /**
     * 对session对象持久化保存。
     */
    public void persist(boolean isLogin) {
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

    }

    private Date getExpiredTime(long time) {
        Date date = null;
        if (maxInactiveInterval <= 0) {
            try {
                date = DateUtils.parse("9999-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                LOGGER.warn("解析时间错误", e);
            }
        } else {
            date = new Date();
            date.setTime(time + TimeUnit.SECONDS.toMillis((long)this.maxInactiveInterval));
        }

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
            Object obj = null;
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
