package org.horx.wdf.sys.view.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker菜单权限Tag。
 * @since 1.0
 */
public class MenuAllowedTag implements TemplateDirectiveModel {

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private AccessPermissionService permissionService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String code = (map.get("code") == null) ? null : map.get("code").toString();
        if (StringUtils.isEmpty(code)) {
            return;
        }
        Long userId = sysContextHolder.getUserId();
        if (userId == null || !permissionService.isMenuAllowedForUser(userId, code)) {
            return;
        }
        templateDirectiveBody.render(environment.getOut());
    }

}
