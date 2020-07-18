package org.horx.wdf.sys.view;

import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 菜单页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/menu")
public class MenuPageController {

    @Autowired
    private MenuService menuService;

    @AccessPermission("sys.menu.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/menu/list");
        return mav;
    }

    @AccessPermission("sys.menu.create")
    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("parentId") Long parentId) {
        if (parentId == null) {
            throw new RuntimeException("参数错误，parentId不能为null");
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/menu/edit");

        MenuDTO menu = menuService.getById(parentId);
        if (menu == null) {
            throw new RuntimeException("父菜单不存在，parentId=" + parentId);
        }
        mav.addObject("parentId", parentId);
        mav.addObject("parentName", menu.getName());

        return mav;
    }

    @AccessPermission("sys.menu.modify")
    @RequestMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.setViewName("sys/menu/edit");
        return mav;
    }
}
