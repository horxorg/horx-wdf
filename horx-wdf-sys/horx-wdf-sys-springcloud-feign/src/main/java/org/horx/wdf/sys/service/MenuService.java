package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
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
 * 菜单Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/menu")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface MenuService {

    @GetMapping("/getById")
    MenuDTO getById(@RequestParam Long id);

    @PostMapping("/query")
    List<MenuDTO> query(@RequestBody PaginationQuery<MenuQueryDTO> paginationQuery);

    @PostMapping("/create")
    Long create(@RequestBody MenuDTO menuDTO);

    @PostMapping("/modify")
    void modify(@RequestBody MenuDTO menuDTO);

    @PostMapping("/remove")
    void remove(@RequestParam Long[] ids);
}
