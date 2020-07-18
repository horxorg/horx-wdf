package org.horx.wdf.sys.vo;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.field.annotation.FieldDesc;
import org.horx.wdf.common.filed.value.annotation.PathParameter;
import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户VO。
 * @since 1.0
 */
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter(groups = {Groups.Modify.class})
    @NotNull(groups = {Groups.Modify.class})
    private Long id;

    @FieldDesc(name = "${sys.user.name}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String name;

    @FieldDesc(name = "${sys.user.username}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String username;

    @FieldDesc(name = "${sys.user.password}")
    @RequestParameter(groups = {Groups.Create.class})
    @NotEmpty(groups = {Groups.Create.class})
    private String password;

    private Date pwdModifyTime;

    private Date pwdErrLockTime;

    private Date pwdErrUnlockTime;

    @RequestParameter(groups = {Groups.Default.class})
    private Long orgId;

    private String orgName;

    @RequestParameter(groups = {Groups.Default.class})
    private String mobile;

    @RequestParameter(groups = {Groups.Default.class})
    private String phone;

    @RequestParameter(groups = {Groups.Default.class})
    private String email;

    @RequestParameter(groups = {Groups.Default.class})
    private String status;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Boolean builtIn;

    @RequestParameter(groups = {Groups.Default.class})
    private String remarks;

    private Long version;

    private Date createTime;

    private Long createUserId;

    private Long createOrgId;

    private Date modifyTime;

    private Long modifyUserId;

    @RequestParameter(groups = {Groups.Default.class}, key="roleIds[]")
    private Long[] roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPwdModifyTime() {
        return pwdModifyTime;
    }

    public void setPwdModifyTime(Date pwdModifyTime) {
        this.pwdModifyTime = pwdModifyTime;
    }

    public Date getPwdErrLockTime() {
        return pwdErrLockTime;
    }

    public void setPwdErrLockTime(Date pwdErrLockTime) {
        this.pwdErrLockTime = pwdErrLockTime;
    }

    public Date getPwdErrUnlockTime() {
        return pwdErrUnlockTime;
    }

    public void setPwdErrUnlockTime(Date pwdErrUnlockTime) {
        this.pwdErrUnlockTime = pwdErrUnlockTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Boolean getBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(Boolean builtIn) {
        this.builtIn = builtIn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(Long createOrgId) {
        this.createOrgId = createOrgId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
