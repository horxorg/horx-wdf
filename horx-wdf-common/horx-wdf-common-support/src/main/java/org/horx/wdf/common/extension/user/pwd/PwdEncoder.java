package org.horx.wdf.common.extension.user.pwd;

import org.horx.wdf.common.extension.user.CommonUser;

/**
 * 密码Encoder。
 * @since 1.0
 */
public interface PwdEncoder {

    /**
     * 修改密码。
     * @param user
     */
    void modifyPwd(CommonUser user);

    /**
     * 转换密码。
     * @param user
     * @return
     */
    String encodePwd(CommonUser user);
}
