package org.horx.wdf.sys.view;

import org.horx.common.utils.DateUtils;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 数据操作日志页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/dataLog")
public class DataOperationLogPageController {

    @AccessPermission("sys.dataLog.query")
    @RequestMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.setViewName("sys/dataLog/detail");
        return mav;
    }

    @AccessPermission("sys.dataLog.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        Date date = new Date();
        mav.addObject("endTime", DateUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
        date = DateUtils.addDay(date, -1) ;
        mav.addObject("startTime", DateUtils.format(date, "yyyy-MM-dd HH:mm:ss"));

        mav.setViewName("sys/dataLog/list");
        return mav;
    }

}
