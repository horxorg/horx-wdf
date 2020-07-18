package org.horx.wdf.common.extension.user;

import java.util.Date;

/**
 * 用户。
 * @since 1.0
 */
public interface CommonUser {

    Long getId();

    void setId(Long id);

    String getPassword();

    void setPassword(String password);

    String getPwdEncodeType();

    void setPwdEncodeType(String pwdEncodeType);

    String getPwdSalt();

    void setPwdSalt(String pwdSalt);

    Date getPwdModifyTime();

    void setPwdModifyTime(Date pwdModifyTime);
}
