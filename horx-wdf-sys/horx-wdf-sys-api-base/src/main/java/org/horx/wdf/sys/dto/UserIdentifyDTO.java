package org.horx.wdf.sys.dto;

import java.io.Serializable;

/**
 * 用户认证DTO。
 * @since 1.0
 */
public class UserIdentifyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String vcode;

    private String clientIp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
