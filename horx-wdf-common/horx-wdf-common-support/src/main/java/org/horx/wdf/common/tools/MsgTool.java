package org.horx.wdf.common.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * msg工具类。
 * @since 1.0
 */
public class MsgTool {

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取msg。
     * @param msgKey
     * @return
     */
    public String getMsg(String msgKey) {
        return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
    }

    /**
     * 获取msg。
     * @param msgKey
     * @param args
     * @return
     */
    public String getMsg(String msgKey, String... args) {
        return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
    }

    /**
     * 获取占位符msg。
     * @param msgKey 支持${}、{}格式。
     * @return
     */
    public String getPlaceholderMsg(String msgKey) {
        String newValue = msgKey.trim();
        if (newValue.startsWith("${") && newValue.endsWith("}")) {
            newValue = getMsg(newValue.substring(2, newValue.length() - 1));
        } else if (newValue.startsWith("{") && newValue.endsWith("}")) {
            newValue = getMsg(newValue.substring(1, newValue.length() - 1));
        }
        return newValue;
    }
}
