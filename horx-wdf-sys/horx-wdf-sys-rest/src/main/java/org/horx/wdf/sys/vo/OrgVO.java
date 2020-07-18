package org.horx.wdf.sys.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.horx.common.collection.TreeData;
import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.field.annotation.FieldDesc;
import org.horx.wdf.common.filed.value.annotation.PathParameter;
import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 机构VO。
 * @since 1.0
 */
public class OrgVO implements TreeData<OrgVO>, Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter(groups = {Groups.Modify.class})
    @NotNull(groups = {Groups.Modify.class})
    private Long id;

    @FieldDesc(name = "${sys.org.parent}")
    @RequestParameter(groups = {Groups.Create.class})
    private Long parentId;

    private String parentName;

    @FieldDesc(name = "${sys.org.code}")
    @RequestParameter(groups = {Groups.Default.class})
    private String code;

    @FieldDesc(name = "${sys.org.name}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String name;

    @FieldDesc(name = "${sys.org.type}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String type;

    private Integer levelNum;

    private String levelCode;

    @FieldDesc(name = "${common.enabled}")
    @RequestParameter(groups = {Groups.Default.class})
    private Boolean enabled;

    @FieldDesc(name = "${sys.org.establishDate}")
    @RequestParameter(pattern = "yyyy-MM-dd", groups = {Groups.Default.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date establishDate;

    @FieldDesc(name = "${sys.org.cancelDate}")
    @RequestParameter(pattern = "yyyy-MM-dd", groups = {Groups.Default.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date cancelDate;

    @FieldDesc(name = "${common.displaySeq}")
    @RequestParameter(groups = {Groups.Default.class})
    private Long displaySeq;

    @FieldDesc(name = "${common.remarks}")
    @RequestParameter(groups = {Groups.Default.class})
    private String remarks;

    private Long version;

    private Date createTime;

    private Long createUserId;

    private Long createOrgId;

    private Date modifyTime;

    private Long modifyUserId;

    private List<OrgVO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
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

    @Override
    public List<OrgVO> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<OrgVO> children) {
        this.children = children;
    }
}
