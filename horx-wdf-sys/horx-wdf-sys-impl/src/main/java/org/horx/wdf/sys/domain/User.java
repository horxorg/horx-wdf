package org.horx.wdf.sys.domain;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.extension.user.CommonUser;
import org.horx.wdf.common.filed.value.annotation.Now;
import org.horx.wdf.common.filed.value.annotation.TraceId;
import org.horx.wdf.common.jdbc.annotation.Sequence;
import org.horx.wdf.common.mybatis.value.annotation.Deleted;
import org.horx.wdf.common.mybatis.value.annotation.DeletedRid;
import org.horx.wdf.common.filed.value.annotation.CurrentUserId;
import org.horx.wdf.sys.extension.value.annotation.CurrentUserOrgId;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户。
 * @since 1.0
 */
@Table(name = "wdf_user")
public class User implements CommonUser, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Sequence(name = "seq_wdf_user")
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String pwdSalt;

    @Column
    private Integer pwdStrength;

    @Column
    private String pwdEncodeType;

    @Column
    private Date pwdModifyTime;

    @Column
    private Integer pwdErrTimes;

    @Column
    private Date pwdErrLockTime;

    @Column
    private Date pwdErrUnlockTime;

    @Column
    private Long orgId;

    @Column
    @Transient
    private String orgName;

    @Column
    private String mobile;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String status;

    @Column
    private Date lastLoginTime;

    @Column
    private String lastLoginIp;

    @Column(name = "is_built_in")
    private Boolean builtIn;

    @Column
    private String remarks;

    @Deleted(groups = {Groups.Insert.class, Groups.LogicalDelete.class})
    @Column(name = "is_deleted")
    private Boolean deleted;

    @DeletedRid(groups = {Groups.Insert.class, Groups.LogicalDelete.class})
    @Column
    private Long delRid;

    @Now(groups = {Groups.Insert.class})
    @Column
    private Date createTime;

    @CurrentUserId(groups = {Groups.Insert.class})
    @Column
    private Long createUserId;

    @CurrentUserOrgId(groups = {Groups.Insert.class})
    @Column
    private Long createOrgId;

    @Now(groups = {Groups.Insert.class, Groups.Update.class, Groups.LogicalDelete.class})
    @Column
    private Date modifyTime;

    @CurrentUserId(groups = {Groups.Insert.class, Groups.Update.class, Groups.LogicalDelete.class})
    @Column
    private Long modifyUserId;

    @TraceId(groups = {Groups.Insert.class, Groups.Update.class, Groups.LogicalDelete.class})
    @Column
    private String traceId;

    @Transient
    private Long[] roleIds;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPwdSalt() {
        return pwdSalt;
    }

    @Override
    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

    public Integer getPwdStrength() {
        return pwdStrength;
    }

    public void setPwdStrength(Integer pwdStrength) {
        this.pwdStrength = pwdStrength;
    }

    @Override
    public String getPwdEncodeType() {
        return pwdEncodeType;
    }

    @Override
    public void setPwdEncodeType(String pwdEncodeType) {
        this.pwdEncodeType = pwdEncodeType;
    }

    @Override
    public Date getPwdModifyTime() {
        return pwdModifyTime;
    }

    @Override
    public void setPwdModifyTime(Date pwdModifyTime) {
        this.pwdModifyTime = pwdModifyTime;
    }

    public Integer getPwdErrTimes() {
        return pwdErrTimes;
    }

    public void setPwdErrTimes(Integer pwdErrTimes) {
        this.pwdErrTimes = pwdErrTimes;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getDelRid() {
        return delRid;
    }

    public void setDelRid(Long delRid) {
        this.delRid = delRid;
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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
}
