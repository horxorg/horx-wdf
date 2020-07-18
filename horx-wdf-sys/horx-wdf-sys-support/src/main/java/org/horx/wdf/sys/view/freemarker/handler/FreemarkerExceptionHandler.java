package org.horx.wdf.sys.view.freemarker.handler;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.Writer;

/**
 * Freemarker异常处理。
 * @since 1.0
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
    @Override
    public void handleTemplateException(TemplateException e, Environment environment, Writer writer) throws TemplateException {
        throw new RuntimeException("freemarkder异常", e);
    }
}
