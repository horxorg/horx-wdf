package org.horx.wdf.sys.view;

import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 角色页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/role")
public class RolePageController {

    @Autowired
    private SysContextHolder sysContextHolder;

    @AccessPermission("sys.role.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/role/list");
        return mav;
    }

    @AccessPermission("sys.role.detail")
    @RequestMapping("/detailTab/{id}")
    public ModelAndView detailTab(@PathVariable("id") Long id, @RequestParam(required = false) String usable) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.addObject("usable", usable);
        mav.setViewName("sys/role/detailTab");
        return mav;
    }

    @AccessPermission("sys.role.detail")
    @RequestMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, @RequestParam(required = false) String usable) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.addObject("usable", usable);
        mav.setViewName("sys/role/detail");
        return mav;
    }

    @AccessPermission("sys.role.usable")
    @RequestMapping("/usable/list")
    public ModelAndView usableList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/role/usableList");
        return mav;
    }

    @AccessPermission("sys.role.create")
    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.setViewName("sys/role/edit");
        return mav;
    }

    @AccessPermission("sys.role.modify")
    @RequestMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.addObject("id", id);
        mav.setViewName("sys/role/edit");
        return mav;
    }

    @AccessPermission("sys.role.edit")
    @RequestMapping("/editTab")
    public ModelAndView editTab(@RequestParam(required = false) Long id, @RequestParam(required = false) String currTab) {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.addObject("id", id);
        mav.addObject("currTab", currTab);
        mav.setViewName("sys/role/editTab");
        return mav;
    }

    @AccessPermission("sys.role.edit")
    @RequestMapping("/menu/{id}")
    public ModelAndView menu(@PathVariable Long id, @RequestParam String objType) {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.addObject("id", id);
        mav.addObject("objType", objType);
        mav.setViewName("sys/role/menu");
        return mav;
    }

    @AccessPermission("sys.role.detail")
    @RequestMapping("/detail/menu/{id}")
    public ModelAndView detailMenu(@PathVariable Long id, @RequestParam(required = false) String usable, @RequestParam String objType) {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.addObject("id", id);
        mav.addObject("usable", usable);
        mav.addObject("objType", objType);
        mav.addObject("detail", "1");
        mav.setViewName("sys/role/menu");
        return mav;
    }

    private void addModelAndViewData(ModelAndView mav) {
        Long orgId = sysContextHolder.getUserOrgId();
        mav.addObject("orgId", orgId);
    }
}
