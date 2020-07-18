package org.horx.wdf.sys.view.jsp.tag;

import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.horx.wdf.sys.extension.context.SysContextHolder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * JSP权限项Tag。
 * @since 1.0
 */
public class PermissionAllowedTag extends TagSupport {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String code;

    @Override
    public int doStartTag() throws JspException {
        SysContextHolder sysContextHolder = SpringContext.getBean(SysContextHolder.class);
        Long userId = sysContextHolder.getUserId();
        if (userId == null) {
            return SKIP_BODY;
        }

        AccessPermissionService permissionService = SpringContext.getBean(AccessPermissionService.class);
        if (permissionService.isPermissionAllowedForUser(userId, code)) {
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
