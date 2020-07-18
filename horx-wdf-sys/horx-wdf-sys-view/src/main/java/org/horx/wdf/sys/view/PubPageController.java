package org.horx.wdf.sys.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公用页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/pub")
public class PubPageController {

    @RequestMapping("/icon")
    public ModelAndView icon() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/pub/icon");
        return mav;
    }
}
