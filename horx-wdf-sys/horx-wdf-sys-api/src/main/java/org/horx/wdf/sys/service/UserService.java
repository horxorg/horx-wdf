package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserRoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.UserWithAuthDTO;

import java.util.List;

/**
 * 用户Service。
 * @since 1.0
 */
public interface UserService {
    UserDTO identify(UserIdentifyDTO loginDTO);

    UserDTO getById(Long userId);

    UserDTO getByIdAuthorized(Long userId, SysDataAuthDTO sysDataAuth);

    UserDTO getByIdWithRoleAuthorized(Long userId, SysDataAuthDTO sysDataAuth);

    List<UserRoleDTO> queryForValidRoles(Long userId);

    PaginationResult<UserDTO> paginationQuery(PaginationQuery<UserQueryDTO> paginationQuery);

    Long create(UserWithAuthDTO userWithAuth);

    void modify(UserWithAuthDTO userWithAuth);

    void modifyPwd(Long userId, String pwd, SysDataAuthDTO sysDataAuth);

    void modifyPwdWithCheck(Long userId, String oldPwd, String pwd);

    void remove(BatchWithSysAuthDTO batchWithAuth);

    void unlock(BatchWithSysAuthDTO batchWithAuth);

}
