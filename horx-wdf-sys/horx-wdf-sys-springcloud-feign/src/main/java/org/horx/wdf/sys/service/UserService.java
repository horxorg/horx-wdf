package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserRoleDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.UserWithAuthDTO;
import org.horx.wdf.sys.springcloud.SpringcloudConsumerContextInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/user")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface UserService {

    @PostMapping("/identify")
    UserDTO identify(@RequestBody UserIdentifyDTO loginDTO);

    @GetMapping("/getById")
    UserDTO getById(@RequestParam Long userId);

    @PostMapping("/getByIdAuthorized")
    UserDTO getByIdAuthorized(@RequestParam Long userId, @RequestBody SysDataAuthDTO sysDataAuth);

    @PostMapping("/getByIdWithRoleAuthorized")
    UserDTO getByIdWithRoleAuthorized(@RequestParam Long userId, @RequestBody SysDataAuthDTO sysDataAuth);

    @GetMapping("/queryForValidRoles")
    List<UserRoleDTO> queryForValidRoles(@RequestParam Long userId);

    @PostMapping("/pagingQuery")
    PagingResult<UserDTO> pagingQuery(@RequestBody PagingQuery<UserQueryDTO> pagingQuery);

    @PostMapping("/create")
    Long create(@RequestBody UserWithAuthDTO userWithAuth);

    @PostMapping("/modify")
    void modify(@RequestBody UserWithAuthDTO userWithAuth);

    @PostMapping("/modifyPwd")
    void modifyPwd(@RequestParam Long userId, @RequestParam String pwd, @RequestBody SysDataAuthDTO sysDataAuth);

    @PostMapping("/modifyPwdWithCheck")
    void modifyPwdWithCheck(@RequestParam Long userId, @RequestParam String oldPwd, @RequestParam String pwd);

    @PostMapping("/remove")
    void remove(@RequestBody BatchWithSysAuthDTO batchWithAuth);

    @PostMapping("/unlock")
    void unlock(@RequestBody BatchWithSysAuthDTO batchWithAuth);
}
