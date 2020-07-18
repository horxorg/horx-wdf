package org.horx.wdf.sys.domain;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.filed.value.annotation.CurrentUserId;
import org.horx.wdf.common.filed.value.annotation.Now;
import org.horx.wdf.common.filed.value.annotation.TraceId;
import org.horx.wdf.common.jdbc.annotation.Sequence;
import org.horx.wdf.common.mybatis.value.annotation.Deleted;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单授权。
 * @since 1.0
 */
@Table(name = "wdf_menu_auth")
public class MenuAuthority implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Sequence(name = "seq_wdf_menu_auth")
    private Long id;

    @Column
    private String objType;

    @Column
    private Long objId;

    @Column
    private Long menuId;

    @Column
    private Integer checkedType;

    @Deleted(groups = {Groups.Insert.class, Groups.LogicalDelete.class})
    @Column(name = "is_deleted")
    private Boolean deleted;

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

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getCheckedType() {
        return checkedType;
    }

    public void setCheckedType(Integer checkedType) {
        this.checkedType = checkedType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
