package org.horx.wdf.common.extension.session;

import java.util.List;

/**
 * 会话服务接口。
 * @since 1.0
 */
public interface CommonSessionService {

    /**
     * 获取会话。
     * @param sessionKey
     * @return
     */
    SessionDTO getBySessionKey(String sessionKey);

    /**
     * 创建会话。
     * @param session
     * @return
     */
    Long create(SessionDTO session);

    /**
     * 修改会话。
     * @param session
     */
    void modify(SessionDTO session);

    /**
     * 根据会话key删除会话。
     * @param sessionKey
     */
    void removeBySessionKey(String sessionKey);

    /**
     * 删除过期的会话。
     */
    void removeExpired();

    /**
     * 创建会话属性。
     * @param sessionAttr
     * @return
     */
    Long createAttr(SessionAttrDTO sessionAttr);

    /**
     * 修改会话属性。
     * @param sessionAttr
     */
    void modifyAttr(SessionAttrDTO sessionAttr);

    /**
     * 删除会话属性。
     * @param sessionId
     * @param attrKey
     */
    void removeAttrByKey(Long sessionId, String attrKey);

    /**
     * 获取会话属性。
     * @param sessionId
     * @return
     */
    List<SessionAttrDTO> queryAttrBySessionId(Long sessionId);

}
