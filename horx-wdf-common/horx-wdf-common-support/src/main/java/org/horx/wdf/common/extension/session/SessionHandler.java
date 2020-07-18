package org.horx.wdf.common.extension.session;

/**
 * 会话Handler。
 * @since 1.0
 */
public interface SessionHandler {

    /**
     * 登录。
     * @param userId
     */
    void login(Long userId);

    /**
     * 退出。
     */
    void logout();

    /**
     * 获取当前登录的用户ID。
     * @return
     */
    Long getCurrentUserId();
}
