package org.horx.wdf.sys.vo;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.field.annotation.FieldDesc;
import org.horx.wdf.common.filed.value.annotation.PathParameter;
import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据权限定义VO。
 * @since 1.0
 */
public class DataPermissionDefVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter(groups = {Groups.Modify.class})
    @NotNull(groups = {Groups.Modify.class})
    private Long id;

    @FieldDesc(name = "${sys.dataPermission.code}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String code;

    @FieldDesc(name = "${sys.dataPermission.name}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String name;

    @FieldDesc(name = "${sys.dataPermission.objType}")
    @RequestParameter(groups = {Groups.Create.class})
    @NotEmpty(groups = {Groups.Create.class})
    private String objType;

    @FieldDesc(name = "${sys.dataPermission.obj}")
    @RequestParameter(groups = {Groups.Create.class})
    private Long objId;

    @Transient
    private String objName;

    @FieldDesc(name = "${common.enabled}")
    @RequestParameter(groups = {Groups.Default.class})
    private Boolean enabled;

    @FieldDesc(name = "${common.displaySeq}")
    @RequestParameter(groups = {Groups.Default.class})
    private Long displaySeq;

    @FieldDesc(name = "${common.remarks}")
    @RequestParameter(groups = {Groups.Default.class})
    private String remarks;

    private Long version;

    private Date createTime;

    private Long createUserId;

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
