package org.horx.wdf.sys.dto.query;

import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;

import java.io.Serializable;

/**
 * 字典日志查询条件DTO。
 * @since 1.0
 */
public class DictQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String[] bizTypes;

    private Boolean treeData;

    private DictDataAuthDTO dictDataAuth;

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

    public String[] getBizTypes() {
        return bizTypes;
    }

    public void setBizTypes(String[] bizTypes) {
        this.bizTypes = bizTypes;
    }

    public Boolean getTreeData() {
        return treeData;
    }

    public void setTreeData(Boolean treeData) {
        this.treeData = treeData;
    }

    public DictDataAuthDTO getDictDataAuth() {
        return dictDataAuth;
    }

    public void setDictDataAuth(DictDataAuthDTO dictDataAuth) {
        this.dictDataAuth = dictDataAuth;
    }
}
