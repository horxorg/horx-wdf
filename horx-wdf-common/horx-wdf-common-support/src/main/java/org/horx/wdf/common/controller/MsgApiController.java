package org.horx.wdf.common.controller;

import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.spring.LocaleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.Map;

/**
 * 配置文本API控制器。
 * @since 1.0
 */
@Controller
public class MsgApiController {

    @Autowired
    private WebTool webTool;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/api/public/msg")
    @ResponseBody
    public Result<Map<String, String>> getLocaleMsg() {
        Locale locale = webTool.getLocale();
        Result result = new Result();

        if (messageSource instanceof DelegatingMessageSource) {
            DelegatingMessageSource dms = (DelegatingMessageSource)messageSource;
            MessageSource pms = dms.getParentMessageSource();
            if (pms instanceof LocaleMessageSource) {
                Map<String, String> msg = ((LocaleMessageSource)pms).getMergedMsg(locale);
                result.setData(msg);
            }
        } else if (messageSource instanceof LocaleMessageSource) {
            Map<String, String> msg = ((LocaleMessageSource)messageSource).getMergedMsg(locale);
            result.setData(msg);
        }

        return result;
    }
}
