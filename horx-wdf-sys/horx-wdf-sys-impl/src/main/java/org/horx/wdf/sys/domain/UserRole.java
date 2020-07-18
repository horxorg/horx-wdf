package org.horx.wdf.sys.domain;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.filed.value.annotation.Now;
import org.horx.wdf.common.filed.value.annotation.TraceId;
import org.horx.wdf.common.jdbc.annotation.Sequence;
import org.horx.wdf.common.mybatis.value.annotation.Deleted;
import org.horx.wdf.common.mybatis.value.annotation.DeletedRid;
import org.horx.wdf.common.filed.value.annotation.CurrentUserId;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色。
 * @since 1.0
 */
@Table(name = "wdf_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Sequence(name = "seq_wdf_user_role")
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long roleId;

    @Column(name = "is_enabled")
    private Boolean enabled;

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

    @Now(groups = {Groups.Insert.class, Groups.Update.class, Groups.LogicalDelete.class})
    @Column
    private Date modifyTime;

    @CurrentUserId(groups = {Groups.Insert.class, Groups.Update.class, Groups.LogicalDelete.class})
    @Column
    private Long modifyUserId;

    @TraceId(groups = {Groups.Insert.class, Groups.Update.class, Groups.LogicalDelete.class})
    @Column
    private String traceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}
