package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.domain.UserRole;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;

import java.util.List;

/**
 * 用户Manager。
 * @since 1.0
 */
public interface UserManager {
    User identify(UserIdentifyDTO userIdentifyDTO);

    User getById(Long userId);

    User getByIdAuthorized(Long userId, SysDataAuthDTO sysDataAuth);

    User getByIdWithRoleAuthorized(Long userId, SysDataAuthDTO sysDataAuth);

    List<UserRole> queryForValidRoles(Long userId);

    PaginationResult<User> paginationQuery(UserQueryDTO userQuery, PaginationParam paginationParam);

    void create(User user, SysDataAuthDTO sysDataAuth);

    void modify(User user, SysDataAuthDTO sysDataAuth);

    void modifyPwd(Long userId, String pwd, SysDataAuthDTO sysDataAuth);

    void modifyPwdWithCheck(Long userId, String oldPwd, String pwd);

    void remove(Long[] ids, SysDataAuthDTO sysDataAuth);

    void unlock(Long[] ids, SysDataAuthDTO sysDataAuth);
}
