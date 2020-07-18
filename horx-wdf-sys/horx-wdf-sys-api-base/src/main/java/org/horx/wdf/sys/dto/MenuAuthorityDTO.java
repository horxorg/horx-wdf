package org.horx.wdf.sys.dto;

import java.io.Serializable;

/**
 * 菜单授权DTO。
 * @since 1.0
 */
public class MenuAuthorityDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String objType;

    private Long objId;

    private Long menuId;

    private Integer checkedType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getCheckedType() {
        return checkedType;
    }

    public void setCheckedType(Integer checkedType) {
        this.checkedType = checkedType;
    }
}
