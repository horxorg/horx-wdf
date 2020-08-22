package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserRoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.UserWithAuthDTO;
import org.horx.wdf.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/identify")
    public UserDTO identify(@RequestBody UserIdentifyDTO loginDTO) {
        return userService.identify(loginDTO);
    }

    @GetMapping("/getById")
    public UserDTO getById(@RequestParam Long userId) {
        return userService.getById(userId);
    }

    @PostMapping("/getByIdAuthorized")
    public UserDTO getByIdAuthorized(@RequestParam Long userId, @RequestBody SysDataAuthDTO sysDataAuth) {
        return userService.getByIdAuthorized(userId, sysDataAuth);
    }

    @PostMapping("/getByIdWithRoleAuthorized")
    public UserDTO getByIdWithRoleAuthorized(@RequestParam Long userId, @RequestBody SysDataAuthDTO sysDataAuth) {
        return userService.getByIdWithRoleAuthorized(userId, sysDataAuth);
    }

    @GetMapping("/queryForValidRoles")
    public List<UserRoleDTO> queryForValidRoles(@RequestParam Long userId) {
        return userService.queryForValidRoles(userId);
    }

    @PostMapping("/paginationQuery")
    public PaginationResult<UserDTO> paginationQuery(@RequestBody PaginationQuery<UserQueryDTO> paginationQuery) {
        return userService.paginationQuery(paginationQuery);
    }

    @PostMapping("/create")
    public Long create(@RequestBody UserWithAuthDTO userWithAuth) {
        return userService.create(userWithAuth);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody UserWithAuthDTO userWithAuth) {
        userService.modify(userWithAuth);
    }

    @PostMapping("/modifyPwd")
    public void modifyPwd(@RequestParam Long userId, @RequestParam String pwd, @RequestBody SysDataAuthDTO sysDataAuth) {
        userService.modifyPwd(userId, pwd, sysDataAuth);
    }

    @PostMapping("/modifyPwdWithCheck")
    public void modifyPwdWithCheck(@RequestParam Long userId, @RequestParam String oldPwd, @RequestParam String pwd) {
        userService.modifyPwdWithCheck(userId, oldPwd, pwd);
    }

    @PostMapping("/remove")
    public void remove(@RequestBody BatchWithSysAuthDTO batchWithAuth) {
        userService.remove(batchWithAuth);
    }

    @PostMapping("/unlock")
    public void unlock(@RequestBody BatchWithSysAuthDTO batchWithAuth) {
        userService.unlock(batchWithAuth);
    }
}
