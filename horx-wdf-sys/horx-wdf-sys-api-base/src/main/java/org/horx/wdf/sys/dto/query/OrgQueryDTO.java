package org.horx.wdf.sys.dto.query;

import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;

/**
 * 机构查询条件DTO。
 * @since 1.0
 */
public class OrgQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

    private String treeQueryName;

    private String treeQueryCode;

    private SysDataAuthDTO sysDataAuth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTreeQueryName() {
        return treeQueryName;
    }

    public void setTreeQueryName(String treeQueryName) {
        this.treeQueryName = treeQueryName;
    }

    public String getTreeQueryCode() {
        return treeQueryCode;
    }

    public void setTreeQueryCode(String treeQueryCode) {
        this.treeQueryCode = treeQueryCode;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
