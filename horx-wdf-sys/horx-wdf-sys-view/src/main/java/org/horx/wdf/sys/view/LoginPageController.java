package org.horx.wdf.sys.view;

import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户登录页面控制器。
 * @since 1.0
 */
@Controller
public class LoginPageController {

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private SessionHandler sessionHandler;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        if (sysContextHolder.isLoggedIn()) {
            mav.setViewName("redirect:/main");
        } else {
            mav.setViewName("sys/login");
        }

        return mav;
    }

    @RequestMapping(path = "/")
    public ModelAndView root() {
        ModelAndView mav = new ModelAndView();
        if (sysContextHolder.isLoggedIn()) {
            mav.setViewName("redirect:/main");
        } else {
            mav.setViewName("redirect:/login");
        }

        return mav;
    }

    @RequestMapping(path = "/index")
    public ModelAndView index() {
        return root();
    }

    @RequestMapping(path = "/main")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView();
        UserDTO user = sysContextHolder.getUser();
        if (user != null) {
            mav.addObject("name", user.getName());
            mav.setViewName("sys/main");
        } else {
            mav.setViewName("redirect:/login");
        }

        return mav;
    }

    @RequestMapping(path = "/logout")
    public ModelAndView logout() {
        sessionHandler.logout();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/login");
        return mav;
    }

}
