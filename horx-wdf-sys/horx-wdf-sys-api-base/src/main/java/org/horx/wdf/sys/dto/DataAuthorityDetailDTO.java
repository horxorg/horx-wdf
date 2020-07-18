package org.horx.wdf.sys.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据授权项DTO。
 * @since 1.0
 */
public class DataAuthorityDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long authorityId;

    private String authorityValue;

    private Integer checkedType;

    private Date createTime;

    private Long createUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityValue() {
        return authorityValue;
    }

    public void setAuthorityValue(String authorityValue) {
        this.authorityValue = authorityValue;
    }

    public Integer getCheckedType() {
        return checkedType;
    }

    public void setCheckedType(Integer checkedType) {
        this.checkedType = checkedType;
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
}
