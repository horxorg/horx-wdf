package org.horx.wdf.sys.domain;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.filed.sort.annotation.FieldSort;
import org.horx.wdf.common.filed.value.annotation.Now;
import org.horx.wdf.common.filed.value.annotation.TraceId;
import org.horx.wdf.common.jdbc.annotation.Sequence;
import org.horx.wdf.common.mybatis.value.annotation.Deleted;
import org.horx.wdf.common.mybatis.value.annotation.DeletedRid;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.extension.sort.annotation.DictSort;
import org.horx.wdf.common.filed.value.annotation.CurrentUserId;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据权限定义。
 * @since 1.0
 */
@Table(name = "wdf_data_perm")
public class DataPermissionDef implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Sequence(name = "seq_wdf_data_perm")
    private Long id;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    @DictSort(dictCode = SysConstants.DICT_DATA_PERMISSION_TYPE)
    private String objType;

    @Column
    private Long objId;

    @Transient
    @Column
    private String objName;

    @Column(name = "is_enabled")
    @FieldSort(asc = "is_enabled,code", desc= "is_enabled desc,code desc")
    private Boolean enabled;

    @Column
    @FieldSort(asc = "display_seq,code", desc= "display_seq desc,code desc")
    private Long displaySeq;

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

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getDisplaySeq() {
        return displaySeq;
    }

    public void setDisplaySeq(Long displaySeq) {
        this.displaySeq = displaySeq;
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
