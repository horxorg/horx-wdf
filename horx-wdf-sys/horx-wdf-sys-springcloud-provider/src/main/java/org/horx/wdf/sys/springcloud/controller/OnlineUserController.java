package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线用户控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/onlineUser")
@RestController
public class OnlineUserController {

    @Autowired
    private OnlineUserService onlineUserService;

    @PostMapping("/paginationQuery")
    public PaginationResult<OnlineUserDTO> paginationQuery(@RequestBody PaginationQuery<OnlineUserQueryDTO> paginationQuery) {
        return onlineUserService.paginationQuery(paginationQuery);
    }

    @PostMapping("/offlineCheck")
    public String[] offlineCheck(@RequestBody BatchWithSysAuthDTO batchWithAuth) {
        return onlineUserService.offlineCheck(batchWithAuth);
    }
}
