package org.horx.wdf.sys.dto.query;

import java.io.Serializable;

/**
 * 数据权限日志查询条件DTO。
 * @since 1.0
 */
public class DataPermissionQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String objType;

    private Boolean enabled;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
