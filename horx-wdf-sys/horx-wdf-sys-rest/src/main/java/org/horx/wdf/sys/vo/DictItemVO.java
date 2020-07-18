package org.horx.wdf.sys.vo;

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
 * 字典项VO。
 * @since 1.0
 */
public class DictItemVO implements TreeData<DictItemVO>, Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter(groups = {Groups.Modify.class})
    @NotNull(groups = {Groups.Modify.class})
    private Long id;

    @PathParameter(groups = {Groups.Default.class})
    private Long dictId;

    @RequestParameter(groups = {Groups.Create.class})
    private Long parentId;

    @FieldDesc(name = "${sys.dict.item.code}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String code;

    @FieldDesc(name = "${sys.dict.item.name}")
    @RequestParameter(groups = {Groups.Default.class})
    @NotEmpty(groups = {Groups.Default.class})
    private String name;

    @FieldDesc(name = "${sys.dict.item.fullCode}")
    @RequestParameter(groups = {Groups.Default.class})
    private String fullCode;

    @FieldDesc(name = "${sys.dict.item.simpleCode}")
    @RequestParameter(groups = {Groups.Default.class})
    private String simpleCode;

    private Integer levelNum;

    private String levelCode;

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

    private List<DictItemVO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getSimpleCode() {
        return simpleCode;
    }

    public void setSimpleCode(String simpleCode) {
        this.simpleCode = simpleCode;
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

    @Override
    public List<DictItemVO> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<DictItemVO> children) {
        this.children = children;
    }
}
