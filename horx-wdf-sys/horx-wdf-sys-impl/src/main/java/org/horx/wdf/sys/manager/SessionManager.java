package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.domain.OnlineUser;
import org.horx.wdf.sys.domain.Session;
import org.horx.wdf.sys.domain.SessionAttr;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;

import java.util.List;

/**
 * 会话Manager。
 * @since 1.0
 */
public interface SessionManager {
    void create(Session session);

    void modify(Session session);

    void removeBySessionKey(String sessionKey);

    void removeExpired();

    Session getBySessionKey(String sessionKey);

    void createAttr(SessionAttr sessionAttr);

    void modifyAttr(SessionAttr sessionAttr);

    void removeAttrByKey(Long sessionId, String attrKey);

    List<SessionAttr> queryAttrBySessionId(Long sessionId);

    SessionAttr getAttrByKey(Long sessionId, String attrKey);

    PaginationResult<OnlineUser> paginationQueryOnlineUser(OnlineUserQueryDTO query, PaginationParam paginationParam);

    /**
     * 检查用户是否可下线。
     * @param ids
     * @param sysDataAuth
     * @return 可下线的sessionKey数组。
     */
    String[] offlineCheck(Long[] ids, SysDataAuthDTO sysDataAuth);
}
