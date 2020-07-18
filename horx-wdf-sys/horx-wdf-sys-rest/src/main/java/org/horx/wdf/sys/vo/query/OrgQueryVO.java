package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.extension.value.annotation.DataAuth;

import java.io.Serializable;

/**
 * 机构日志查询条件VO。
 * @since 1.0
 */
public class OrgQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private String code;

    @RequestParameter()
    private String treeQueryName;

    @RequestParameter()
    private String treeQueryCode;

    @DataAuth
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
