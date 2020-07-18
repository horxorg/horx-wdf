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
 * 角色VO。
 * @since 1.0
 */
public class RoleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter(groups = {Groups.Modify.class})
    @NotNull(groups = {Groups.Modify.class})
    private Long id;

    @FieldDesc(name = "${sys.role.code}")
    @RequestParameter(groups = {Groups.Default.class})
    private String code;

    @FieldDesc(name = "${sys.role.name}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String name;

    @FieldDesc(name = "${sys.role.org}")
    @RequestParameter(groups = {Groups.Default.class})
    private Long orgId;

    private String orgName;

    @FieldDesc(name = "${sys.role.subUsable}")
    @RequestParameter(groups = {Groups.Default.class})
    private Boolean subUsable;

    @FieldDesc(name = "${common.enabled}")
    @RequestParameter(groups = {Groups.Default.class})
    private Boolean enabled;

    @FieldDesc(name = "${common.remarks}")
    @RequestParameter(groups = {Groups.Default.class})
    private String remarks;

    private Long version;

    private Date createTime;

    private Long createUserId;

    private Long createOrgId;

    private Date modifyTime;

    private Long modifyUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getSubUsable() {
        return subUsable;
    }

    public void setSubUsable(Boolean subUsable) {
        this.subUsable = subUsable;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
}
