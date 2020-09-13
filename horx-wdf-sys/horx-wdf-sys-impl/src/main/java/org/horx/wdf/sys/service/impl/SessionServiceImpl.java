package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.extension.session.SessionAttrDTO;
import org.horx.wdf.common.extension.session.SessionDTO;
import org.horx.wdf.sys.converter.SessionAttrConverter;
import org.horx.wdf.sys.converter.SessionConverter;
import org.horx.wdf.sys.domain.Session;
import org.horx.wdf.sys.domain.SessionAttr;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.manager.SessionManager;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.horx.wdf.sys.service.OrgService;
import org.horx.wdf.sys.service.SessionService;
import org.horx.wdf.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会话Service实现。
 * @since 1.0
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private SessionConverter sessionConverter;

    @Autowired
    private SessionAttrConverter sessionAttrConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private AccessPermissionService permissionService;


    @Override
    public SessionDTO getBySessionKey(String sessionKey) {
        Session session = sessionManager.getBySessionKey(sessionKey);
        return sessionConverter.toDto(session);
    }

    @Override
    public Long create(SessionDTO sessionDTO) {
        Session session = sessionConverter.fromDto(sessionDTO);
        sessionManager.create(session);
        return session.getId();
    }

    @Override
    public void modify(SessionDTO sessionDTO) {
        Session session = sessionConverter.fromDto(sessionDTO);
        sessionManager.modify(session);
    }

    @Override
    public void removeBySessionKey(String sessionKey) {
        sessionManager.removeBySessionKey(sessionKey);
    }

    @Override
    public void removeExpired() {
        sessionManager.removeExpired();
    }

    @Override
    public Long createAttr(SessionAttrDTO sessionAttrDTO) {
        SessionAttr sessionAttr = sessionAttrConverter.fromDto(sessionAttrDTO);
        sessionManager.createAttr(sessionAttr);
        return sessionAttr.getId();
    }

    @Override
    public void modifyAttr(SessionAttrDTO sessionAttrDTO) {
        SessionAttr sessionAttr = sessionAttrConverter.fromDto(sessionAttrDTO);
        sessionManager.modifyAttr(sessionAttr);
    }

    @Override
    public void removeAttrByKey(Long sessionId, String attrKey) {
        sessionManager.removeAttrByKey(sessionId, attrKey);
    }

    @Override
    public List<SessionAttrDTO> queryAttrBySessionId(Long sessionId) {
        List<SessionAttr> attrList = sessionManager.queryAttrBySessionId(sessionId);
        return sessionAttrConverter.toDtoList(attrList);
    }

    @Override
    public SessionAttrDTO getAttrByKey(Long sessionId, String attrKey) {
        SessionAttr sessionAttr = sessionManager.getAttrByKey(sessionId, attrKey);
        return sessionAttrConverter.toDto(sessionAttr);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userService.getById(userId);
    }

    @Override
    public OrgDTO getOrgById(Long orgId) {
        return orgService.getById(orgId);
    }

    @Override
    public Long[] getRoleIdsByPermissionCode(Long userId, String permissionCode) {
        return permissionService.getRoleIdsByPermissionCode(userId, permissionCode);
    }
}
