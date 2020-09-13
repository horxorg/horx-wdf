package org.horx.wdf.sys.manager.impl;

import org.horx.common.utils.DateUtils;
import org.horx.wdf.common.config.CommonConfig;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.exception.ErrorCodeException;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.enums.OperationTypeEnum;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.sys.consts.SysErrorCodes;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.domain.Role;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.domain.UserRole;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.dataauth.DataValidationTool;
import org.horx.wdf.common.extension.user.pwd.PwdEncoder;
import org.horx.wdf.sys.manager.RoleManager;
import org.horx.wdf.sys.manager.UserManager;
import org.horx.wdf.sys.mapper.UserMapper;
import org.horx.wdf.sys.mapper.UserRoleMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户Manager实现。
 * @since 1.0
 */
@Component("userManager")
public class UserManagerImpl implements UserManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("pwdEncoder")
    private PwdEncoder pwdEncoder;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private DataValidationTool dataValidationTool;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private EntityExtension entityExtension;

    @Autowired
    private CommonConfig commonConfig;

    @Override
    public User identify(UserIdentifyDTO userIdentifyDTO) {
        User user = userMapper.selectByUsername(userIdentifyDTO.getUsername());
        if (user == null) {
            throw new ErrorCodeException("用户不存在", SysErrorCodes.USER_NOT_EXIST);
        }
        if (!SysConstants.USER_ENABLED_STATUS.equals(user.getStatus())) {
            throw new ErrorCodeException("用户未启用", SysErrorCodes.USER_DISABLED);
        }
        if (user.getPwdErrUnlockTime() != null && user.getPwdErrUnlockTime().getTime() > System.currentTimeMillis()) {
            throw new ErrorCodeException("用户被锁定", SysErrorCodes.LOGIN_LOCKED);
        }

        String currPwd = user.getPassword();

        user.setPassword(userIdentifyDTO.getPassword());
        String encodedPwd = pwdEncoder.encodePwd(user);

        if (!currPwd.equals(encodedPwd)) {
            User modifyUser = entityExtension.newEntity(User.class, true);
            int errTimes = (user.getPwdErrTimes() == null) ? 0 : user.getPwdErrTimes();
            if (errTimes > 0 && user.getPwdErrUnlockTime() != null &&
                    user.getPwdErrUnlockTime().getTime() <= System.currentTimeMillis()) {
                errTimes = 0;
                modifyUser.setPwdErrLockTime(null);
                modifyUser.setPwdErrUnlockTime(null);
            }
            errTimes++;

            modifyUser.setId(user.getId());
            modifyUser.setPwdErrTimes(errTimes);
            if (commonConfig.getPwdErrTimes() > 0 && errTimes >= commonConfig.getPwdErrTimes()) {
                Date now = new Date();
                modifyUser.setPwdErrLockTime(now);
                modifyUser.setPwdErrUnlockTime(getUnlockTime(now));
            }
            userMapper.update(modifyUser);

            throw new ErrorCodeException("密码不正确", SysErrorCodes.LOGIN_PWD_INVALID);
        } else if (user.getPwdErrTimes() != null && user.getPwdErrTimes() > 0) {
            User modifyUser = entityExtension.newEntity(User.class, true);
            modifyUser.setId(user.getId());
            modifyUser.setPwdErrTimes(0);
            modifyUser.setPwdErrLockTime(null);
            modifyUser.setPwdErrUnlockTime(null);
            userMapper.update(modifyUser);
        }

        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public User getById(Long userId) {
        User user = userMapper.selectById(userId);
        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public User getByIdAuthorized(Long userId, SysDataAuthDTO sysDataAuth) {
        User user = userMapper.selectByIdAuthorized(userId, sysDataAuth);
        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public User getByIdWithRoleAuthorized(Long userId, SysDataAuthDTO sysDataAuth) {
        if (userId == null) {
            return null;
        }

        User user = getByIdAuthorized(userId, sysDataAuth);
        if (user != null) {
            List<UserRole> list = queryForValidRoles(userId);
            Long[] roleIdArr = new Long[list.size()];
            int i = 0;
            for (UserRole userRole : list) {
                roleIdArr[i++] = userRole.getRoleId();
            }
            user.setRoleIds(roleIdArr);
        }
        return user;
    }

    @Override
    public List<UserRole> queryForValidRoles(Long userId) {
        List<UserRole> list = userRoleMapper.selectByUserId(userId);
        return list;
    }

    @Override
    public PaginationResult<User> paginationQuery(UserQueryDTO userQuery, PaginationParam paginationParam) {
        return userMapper.paginationSelect(userQuery, new PaginationRowBounds(paginationParam));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}", paramIndex = {0})
    public void create(User user, SysDataAuthDTO sysDataAuth) {
        validate(user, sysDataAuth);
        validateRole(user, sysDataAuth);

        pwdEncoder.modifyPwd(user);

        userMapper.insert(user);
        saveUserRole(user, false);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}", paramIndex = {0})
    public void modify(User user, SysDataAuthDTO sysDataAuth) {
        User old = getByIdAuthorized(user.getId(), sysDataAuth);
        if (old == null) {
            throw new PermissionDeniedException();
        }
        if (old.getOrgId() == null && user.getOrgId() != null ||
                old.getOrgId() != null && !old.getOrgId().equals(user.getOrgId())) {
            validate(user, sysDataAuth);
        }
        validateRole(user, sysDataAuth);

        userMapper.update(user);
        saveUserRole(user, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}",
            desc = "${sys.user.password.modify}", paramIndex = {0})
    public void modifyPwd(Long userId, String pwd, SysDataAuthDTO sysDataAuth) {
        User old = getByIdAuthorized(userId, sysDataAuth);
        if (old == null) {
            throw new PermissionDeniedException();
        }
        User userMod = entityExtension.newEntity(User.class, true);
        userMod.setId(userId);
        userMod.setPassword(pwd);
        pwdEncoder.modifyPwd(userMod);

        userMapper.update(userMod);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}", paramIndex = {0})
    public void remove(Long[] ids, SysDataAuthDTO sysDataAuth) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            User old = getByIdAuthorized(id, sysDataAuth);
            if (old == null) {
                throw new PermissionDeniedException();
            }

            userMapper.logicalDelete(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}",
            operationType = OperationTypeEnum.MODIFY, desc = "${sys.user.unlock}", paramIndex = {0})
    public void unlock(Long[] ids, SysDataAuthDTO sysDataAuth) {
        if (ids == null || ids.length == 0) {
            return;
        }

        User userModify = entityExtension.newEntity(User.class, true);
        userModify.setPwdErrTimes(0);
        userModify.setPwdErrLockTime(null);
        userModify.setPwdErrUnlockTime(null);
        for (Long id : ids) {
            User old = getByIdAuthorized(id, sysDataAuth);
            if (old == null) {
                throw new PermissionDeniedException();
            }

            if (old.getPwdErrLockTime() == null) {
                continue;
            }

            userModify.setId(id);
            userMapper.update(userModify);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.user}",
            desc = "${sys.user.password.modify}", paramIndex = {0})
    public void modifyPwdWithCheck(Long userId, String oldPwd, String pwd) {
        User user = userMapper.selectForPwd(userId);
        String currPwd = user.getPassword();

        user.setPassword(oldPwd);
        String encodedPwd = pwdEncoder.encodePwd(user);

        if (user == null || !currPwd.equals(encodedPwd)) {
            throw new ErrorCodeException("原密码不正确", SysErrorCodes.OLD_PWD_INVALID);
        }

        User userMod = entityExtension.newEntity(User.class, true);
        userMod.setId(userId);
        userMod.setPassword(pwd);
        pwdEncoder.modifyPwd(userMod);
        userMapper.update(userMod);
    }

    private void saveUserRole(User user, boolean modify) {
        List<UserRole> oldList = null;
        if (modify) {
            oldList = userRoleMapper.selectByUserId(user.getId());
        }

        Long[] newRoleIdArr = user.getRoleIds();

        if (oldList != null && oldList.size() > 0) {
            for (UserRole item : oldList) {
                boolean exists = false;
                if (newRoleIdArr != null && newRoleIdArr.length > 0) {
                    for (Long roleId : newRoleIdArr) {
                        if (item.getRoleId().equals(roleId)) {
                            exists = true;
                            break;
                        }
                    }
                }

                if (!exists) {
                    userRoleMapper.logicalDelete(item.getId());
                }
            }
        }

        if (newRoleIdArr != null && newRoleIdArr.length > 0) {
            for (Long roleId : newRoleIdArr) {
                if (roleId == null) {
                    continue;
                }

                boolean exists = false;
                if (oldList != null && oldList.size() > 0) {
                    for (UserRole item : oldList) {
                        if (item.getRoleId().equals(roleId)) {
                            exists = true;
                            break;
                        }
                    }
                }

                if (!exists) {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(roleId);
                    userRole.setEnabled(true);
                    userRoleMapper.insert(userRole);
                }
            }
        }
    }

    private void validate(User user, SysDataAuthDTO sysDataAuth) {
        if (user.getOrgId() == null) {
            if (sysContextHolder.getUserOrgId() == null) {
                return;
            } else {
                throw new PermissionDeniedException();
            }
        }
        boolean authorized = dataValidationTool.validate(sysDataAuth.getOrgAuth(), user.getOrgId());
        if (!authorized) {
            throw new PermissionDeniedException();
        }
    }

    private void validateRole(User user, SysDataAuthDTO sysDataAuth) {
        Long[] roleIds = user.getRoleIds();
        if (roleIds == null || roleIds.length == 0) {
            return;
        }

        for (Long roleId : roleIds) {
            Role role = roleManager.getByIdUsable(roleId, sysDataAuth);
            if (role == null) {
                throw new PermissionDeniedException();
            }
        }
    }

    private Date getUnlockTime(Date lockTime) {
        Date date = null;
        if (commonConfig.getPwdErrLockSeconds() <= 0) {
            try {
                date = DateUtils.parse("9999-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                logger.warn("解析时间错误", e);
            }
        } else {
            date = new Date();
            date.setTime(lockTime.getTime() + TimeUnit.SECONDS.toMillis((long)commonConfig.getPwdErrLockSeconds()));
        }

        return date;
    }
}
