package org.horx.wdf.common.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * 配置项。
 * @since 1.0
 */
public class CommonConfig {

    @Value("${common.session.userId}")
    private String userIdSessionKey;

    @Value("${common.session.useAttr}")
    private boolean sessionUseAttr;

    @Value("${common.user.pwdErrTimes}")
    private int pwdErrTimes;

    @Value("${common.user.pwdErrLockSeconds}")
    private int pwdErrLockSeconds;

    public String getUserIdSessionKey() {
        return userIdSessionKey;
    }

    public boolean isSessionUseAttr() {
        return sessionUseAttr;
    }

    /**
     * 获取密码错误次数上限。
     * 达到该值用户被锁定。
     * 值小于等于0时不做限制。
     * @return
     */
    public int getPwdErrTimes() {
        return pwdErrTimes;
    }

    /**
     * 用户密码错误被锁定后，用户被锁定的时长（秒）。
     * 值小于等于0时用户一直被锁定，直到管理员解锁。
     * @return
     */
    public int getPwdErrLockSeconds() {
        return pwdErrLockSeconds;
    }
}
