package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.converter.UserConverter;
import org.horx.wdf.sys.converter.UserRoleConverter;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.domain.UserRole;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserRoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.UserWithAuthDTO;
import org.horx.wdf.sys.manager.UserManager;
import org.horx.wdf.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service实现。
 * @since 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRoleConverter userRoleConverter;

    @Override
    public UserDTO identify(UserIdentifyDTO loginDTO) {
        User user = userManager.identify(loginDTO);
        UserDTO userDTO = userConverter.toDto(user);
        return userDTO;
    }

    @Override
    public UserDTO getById(Long userId) {
        User user = userManager.getById(userId);
        UserDTO userDTO = userConverter.toDto(user);
        return userDTO;
    }

    @Override
    public UserDTO getByIdAuthorized(Long userId, SysDataAuthDTO sysDataAuth) {
        User user = userManager.getByIdAuthorized(userId, sysDataAuth);
        UserDTO userDTO = userConverter.toDto(user);
        return userDTO;
    }

    @Override
    public UserDTO getByIdWithRoleAuthorized(Long userId, SysDataAuthDTO sysDataAuth) {
        User user = userManager.getByIdWithRoleAuthorized(userId, sysDataAuth);
        UserDTO userDTO = userConverter.toDto(user);
        return userDTO;
    }

    @Override
    public List<UserRoleDTO> queryForValidRoles(Long userId) {
        List<UserRole> list = userManager.queryForValidRoles(userId);
        List<UserRoleDTO> dtoList = userRoleConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public PagingResult<UserDTO> pagingQuery(PagingQuery<UserQueryDTO> pagingQuery) {
        PagingResult<User> pagingResult = userManager.pagingQuery(pagingQuery.getQuery(), pagingQuery.getPagingParam());

        PagingResult<UserDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<UserDTO> dtoList = userConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }

    @Override
    public Long create(UserWithAuthDTO userWithAuth) {
        UserDTO userDTO = userWithAuth.getUser();
        User user = userConverter.fromDto(userDTO);
        userManager.create(user, userWithAuth.getSysDataAuth());
        return user.getId();
    }

    @Override
    public void modify(UserWithAuthDTO userWithAuth) {
        UserDTO userDTO = userWithAuth.getUser();
        User user = userConverter.fromDto(userDTO);
        userManager.modify(user, userWithAuth.getSysDataAuth());
    }

    @Override
    public void modifyPwd(Long userId, String pwd, SysDataAuthDTO sysDataAuth) {
        userManager.modifyPwd(userId, pwd, sysDataAuth);
    }

    @Override
    public void modifyPwdWithCheck(Long userId, String oldPwd, String pwd) {
        userManager.modifyPwdWithCheck(userId, oldPwd, pwd);
    }

    @Override
    public void remove(BatchWithSysAuthDTO batchWithAuth) {
        userManager.remove(batchWithAuth.getIds(), batchWithAuth.getSysDataAuth());
    }

    @Override
    public void unlock(BatchWithSysAuthDTO batchWithAuth) {
        userManager.unlock(batchWithAuth.getIds(), batchWithAuth.getSysDataAuth());
    }
}
