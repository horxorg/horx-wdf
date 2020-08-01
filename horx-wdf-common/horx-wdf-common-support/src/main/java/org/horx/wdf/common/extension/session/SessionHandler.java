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

    /**
     * 设置Session变量。
     * @param attrKey
     * @param attrValue
     *
     * @since 1.0.1
     */
    void setSessionAttr(String attrKey, Object attrValue);

    /**
     * 获取Session变量。
     * @param attrKey
     * @return
     *
     * @since 1.0.1
     */
    Object getSessionAttr(String attrKey);

    /**
     * 移除Session变量。
     * @param attrKey
     *
     * @since 1.0.1
     */
    void removeSessionAttr(String attrKey);
}
