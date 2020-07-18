package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import java.io.Serializable;

/**
 * 菜单查询条件VO。
 * @since 1.0
 */
public class MenuQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private String code;

    private String type;

    private Boolean visible;

    private Boolean enabled;

    @RequestParameter()
    private String treeQueryName;

    @RequestParameter()
    private String treeQueryCode;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
}
