package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/menu")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/getById")
    public MenuDTO getById(@RequestParam Long id) {
        return menuService.getById(id);
    }

    @PostMapping("/query")
    public List<MenuDTO> query(@RequestBody PaginationQuery<MenuQueryDTO> paginationQuery) {
        return menuService.query(paginationQuery);
    }

    @PostMapping("/create")
    public Long create(@RequestBody MenuDTO menuDTO) {
        return menuService.create((menuDTO));
    }

    @PostMapping("/modify")
    public void modify(@RequestBody MenuDTO menuDTO) {
        menuService.modify(menuDTO);
    }

    @PostMapping("/remove")
    public void remove(@RequestParam Long[] ids) {
        menuService.remove(ids);
    }
}
