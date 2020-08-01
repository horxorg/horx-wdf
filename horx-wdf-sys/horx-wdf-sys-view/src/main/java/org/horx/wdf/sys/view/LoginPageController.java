package org.horx.wdf.sys.view;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private WebTool webTool;

    @Autowired
    private SysConfig sysConfig;

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        if (sysContextHolder.isLoggedIn()) {
            String returnUrl = request.getParameter("returnUrl");
            if (StringUtils.isEmpty(returnUrl)) {
                returnUrl = webTool.getResourceAbsolutePath(sysConfig.getMainUrl());
            }

            mav.setViewName("redirect:" + returnUrl);
        } else {
            mav.setViewName("sys/login");
        }

        return mav;
    }

    @RequestMapping(path = "/")
    public ModelAndView root() {
        ModelAndView mav = new ModelAndView();
        if (sysContextHolder.isLoggedIn()) {
            mav.setViewName("redirect:" +  webTool.getResourceAbsolutePath(sysConfig.getMainUrl()));
        } else {
            mav.setViewName("redirect:" +  webTool.getResourceAbsolutePath(sysConfig.getLoginUrl()));
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
            mav.addObject("logoutUrl", webTool.getResourceAbsolutePath(sysConfig.getLogoutUrl()));
            mav.setViewName("sys/main");
        } else {
            mav.setViewName("redirect:" +  webTool.getResourceAbsolutePath(sysConfig.getLoginUrl()));
        }

        return mav;
    }

    @RequestMapping(path = "/logout")
    public ModelAndView logout() {
        sessionHandler.logout();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" +  webTool.getResourceAbsolutePath(sysConfig.getLoginUrl()));
        return mav;
    }

}
