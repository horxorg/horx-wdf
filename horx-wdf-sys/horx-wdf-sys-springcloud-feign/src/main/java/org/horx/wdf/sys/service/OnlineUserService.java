package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.springcloud.SpringcloudConsumerContextInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 在线用户Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/onlineUser")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface OnlineUserService {

    @PostMapping("/pagingQuery")
    PagingResult<OnlineUserDTO> pagingQuery(@RequestBody PagingQuery<OnlineUserQueryDTO> pagingQuery);

    @PostMapping("/offlineCheck")
    String[] offlineCheck(@RequestBody BatchWithSysAuthDTO batchWithAuth);
}
