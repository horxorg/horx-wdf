package org.horx.wdf.common.controller;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.spring.ExceptionResolver;
import org.horx.wdf.common.tools.WebTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录页面控制器。
 * @since 1.0
 */
@Controller
public class ErrorPageController {
    private final static Logger logger = LoggerFactory.getLogger(ErrorPageController.class);

    @Autowired
    private WebTool webTool;

    @Autowired
    private ThreadContextHolder threadContextHolder;

    @Autowired
    private ExceptionResolver exceptionResolver;

    @Value("${common.errorView:/error}")
    private String exViewName;

    @RequestMapping(path = "/err")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        Exception ex = threadContextHolder.getEx();
        logger.error(request.getRequestURI(), ex);

        Object result = exceptionResolver.convertExByResultConverter(ex);

        if (isAjax(request)) {
            response.setStatus(HttpStatus.OK.value()); //设置状态码
            response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");

            try {
                String json = JsonUtils.toJson(result);
                response.getWriter().write(json);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            return mav;
        }

        mav.addObject("result",result);
        mav.addObject("staticVer", webTool.getStaticVer());
        mav.addObject("loadJsSrc", webTool.isLoadJsSrc());
        mav.setViewName(exViewName);
        return mav;
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
