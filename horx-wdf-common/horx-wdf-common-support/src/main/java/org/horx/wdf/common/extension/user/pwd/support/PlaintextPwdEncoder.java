package org.horx.wdf.common.extension.user.pwd.support;

import org.horx.wdf.common.extension.user.CommonUser;
import org.horx.wdf.common.extension.user.pwd.PwdEncoder;

import java.util.Date;

/**
 * 明文密码Encoder。
 * @since 1.0
 */
public class PlaintextPwdEncoder implements PwdEncoder {

    @Override
    public void modifyPwd(CommonUser user) {
        user.setPwdSalt("");
        user.setPwdModifyTime(new Date());
    }

    @Override
    public String encodePwd(CommonUser user) {
        return user.getPassword();
    }
}
