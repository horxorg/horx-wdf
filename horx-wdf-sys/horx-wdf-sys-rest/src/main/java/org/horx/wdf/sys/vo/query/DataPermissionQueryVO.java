package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import java.io.Serializable;

/**
 * 数据权限查询条件VO。
 * @since 1.0
 */
public class DataPermissionQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String code;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private String objType;

    @RequestParameter
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
