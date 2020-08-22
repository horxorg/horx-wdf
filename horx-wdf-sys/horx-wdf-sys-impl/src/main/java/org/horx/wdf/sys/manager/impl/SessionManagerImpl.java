package org.horx.wdf.sys.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.config.CommonConfig;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.enums.OperationTypeEnum;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.domain.OnlineUser;
import org.horx.wdf.sys.domain.Session;
import org.horx.wdf.sys.domain.SessionAttr;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.sys.manager.UserManager;
import org.horx.wdf.sys.mapper.SessionAttrMapper;
import org.horx.wdf.sys.mapper.SessionMapper;
import org.horx.wdf.sys.mapper.UserMapper;
import org.horx.wdf.sys.manager.SessionManager;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会话Manager实现。
 * @since 1.0
 */
@Component("sessionManager")
public class SessionManagerImpl implements SessionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManagerImpl.class);

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionAttrMapper sessionAttrMapper;

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommonConfig commonConfig;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void create(Session session) {
        boolean update = false;
        if (StringUtils.isNotEmpty(session.getSessionKey())) {
            Session found = sessionMapper.selectBySessionKey(session.getSessionKey());
            if (found != null) {
                if (found.getUserId() == null && session.getUserId() != null) {
                    found.setCreateTime(session.getCreateTime());
                }

                found.setLastAccessTime(session.getLastAccessTime());
                found.setExpiredTime(session.getExpiredTime());
                found.setUserId(session.getUserId());
                sessionMapper.update(found);
                session.setId(found.getId());
                update = true;
            }
        }

        if (!update) {
            sessionMapper.insert(session);
        }

        if (session.getUserId() != null) {
            saveUserLastLogin(session);
        }
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void modify(Session session) {
        sessionMapper.update(session);

        if (session.getUserId() != null) {
            saveUserLastLogin(session);
        }
    }

    @Override
    public void removeBySessionKey(String sessionIdStr) {
        sessionMapper.deleteBySessionKey(sessionIdStr);
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void removeExpired() {
        int delCnt = sessionMapper.deleteExpired();
        if (delCnt > 0) {
            LOGGER.info("数据库删除过期session{}条", delCnt);
        }

        if (commonConfig.isSessionUseAttr()) {
            sessionAttrMapper.deleteExpired();
        }
    }

    @Override
    public void createAttr(SessionAttr sessionAttr) {
        sessionAttrMapper.insert(sessionAttr);
    }

    @Override
    public void modifyAttr(SessionAttr sessionAttr) {
        if (sessionAttr.getId() == null) {
            sessionAttrMapper.updateAttrValue(sessionAttr);
        } else {
            sessionAttrMapper.update(sessionAttr);
        }
    }

    @Override
    public void removeAttrByKey(Long sessionId, String attrKey) {
        sessionAttrMapper.deleteByAttrKey(sessionId, attrKey);
    }

    @Override
    public Session getBySessionKey(String sessionKey) {
        return sessionMapper.selectBySessionKey(sessionKey);
    }

    @Override
    public List<SessionAttr> queryBySessionId(Long sessionId) {
        return sessionAttrMapper.select(sessionId);
    }

    @Override
    public PaginationResult<OnlineUser> paginationQueryOnlineUser(OnlineUserQueryDTO query, PaginationParam paginationParam) {
        return sessionMapper.paginationSelectOnlineUser(query, new PaginationRowBounds(paginationParam));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}",
            operationType = OperationTypeEnum.MODIFY, desc = "${sys.onlineUser.offline}", paramIndex = {0})
    public String[] offlineCheck(Long[] ids, SysDataAuthDTO sysDataAuth) {
        if (ids == null || ids.length == 0) {
            return new String[0];
        }

        List<String> sessionKeyList = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Session session = sessionMapper.selectById(id);
            if (session == null || session.getUserId() == null) {
                continue;
            }

            User user = userManager.getByIdAuthorized(session.getUserId(), sysDataAuth);
            if (user == null) {
                throw new PermissionDeniedException();
            }

            sessionKeyList.add(session.getSessionKey());
        }

        return sessionKeyList.toArray(new String[0]);
    }

    private void saveUserLastLogin(Session session) {
        User user = new User();
        user.setId(session.getUserId());
        user.setLastLoginIp(session.getClientIp());
        user.setLastLoginTime(session.getCreateTime());
        userMapper.update(user);
    }
}
