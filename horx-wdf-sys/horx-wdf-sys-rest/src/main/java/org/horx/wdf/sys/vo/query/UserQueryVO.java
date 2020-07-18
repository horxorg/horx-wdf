package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.extension.value.annotation.DataAuth;

import java.io.Serializable;

/**
 * 用户日志查询条件VO。
 * @since 1.0
 */
public class UserQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private String username;

    @RequestParameter()
    private Long[] orgIds;

    @RequestParameter()
    private String mobile;

    @RequestParameter()
    private String phone;

    @RequestParameter()
    private String email;

    @RequestParameter()
    private String[] status;

    @RequestParameter()
    private Integer pwdErrLocked;

    @DataAuth
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
