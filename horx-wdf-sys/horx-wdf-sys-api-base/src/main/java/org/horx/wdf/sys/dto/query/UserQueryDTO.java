package org.horx.wdf.sys.dto.query;

import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 用户查询条件DTO。
 * @since 1.0
 */
public class UserQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String username;

    private Long[] orgIds;

    private String mobile;

    private String phone;

    private String email;

    private String[] status;

    private Integer pwdErrLocked;

    private SysDataAuthDTO sysDataAuth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public Integer getPwdErrLocked() {
        return pwdErrLocked;
    }

    public void setPwdErrLocked(Integer pwdErrLocked) {
        this.pwdErrLocked = pwdErrLocked;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
