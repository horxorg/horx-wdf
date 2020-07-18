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
 * 数据授权VO。
 * @since 1.0
 */
public class DataAuthorityVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter(groups = {Groups.Modify.class})
    @NotNull(groups = {Groups.Modify.class})
    private Long id;

    @RequestParameter(groups = {Groups.Create.class})
    @NotNull(groups = {Groups.Create.class})
    private Long dataPermissionId;

    private String objType;

    private Long objId;

    @FieldDesc(name = "${sys.dataAuth.authorityType}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String authorityType;

    private Integer detailCount;


    @FieldDesc(name = "${common.enabled}")
    @RequestParameter(groups = {Groups.Default.class})
    private Boolean enabled;

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

    public Long getDataPermissionId() {
        return dataPermissionId;
    }

    public void setDataPermissionId(Long dataPermissionId) {
        this.dataPermissionId = dataPermissionId;
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

    public String getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(String authorityType) {
        this.authorityType = authorityType;
    }

    public Integer getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(Integer detailCount) {
        this.detailCount = detailCount;
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
