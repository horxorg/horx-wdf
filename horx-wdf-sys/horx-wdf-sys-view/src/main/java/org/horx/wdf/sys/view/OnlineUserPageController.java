package org.horx.wdf.sys.view;

import org.horx.wdf.sys.annotation.AccessPermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 在线用户页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/onlineUser")
public class OnlineUserPageController {

    @AccessPermission("sys.onlineUser.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/onlineUser/list");
        return mav;
    }

}
