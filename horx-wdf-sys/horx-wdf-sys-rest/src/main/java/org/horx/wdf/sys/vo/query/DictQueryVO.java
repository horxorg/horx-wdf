package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.extension.value.annotation.DataAuth;

import java.io.Serializable;

/**
 * 字典查询条件VO。
 * @since 1.0
 */
public class DictQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String code;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private String[] bizTypes;

    @RequestParameter()
    private Boolean treeData;

    @DataAuth
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
