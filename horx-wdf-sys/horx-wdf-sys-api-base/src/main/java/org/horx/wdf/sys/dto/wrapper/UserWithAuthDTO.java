package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 用户操作DTO。
 * @since 1.0
 */
public class UserWithAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserDTO user;

    private SysDataAuthDTO sysDataAuth;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
