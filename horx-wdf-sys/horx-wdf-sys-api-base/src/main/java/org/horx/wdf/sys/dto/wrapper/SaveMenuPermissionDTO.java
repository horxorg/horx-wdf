package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

import java.io.Serializable;
import java.util.Map;

/**
 * 保存菜单权限的DTO。
 * @since 1.0
 */
public class SaveMenuPermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String menuAuthorityObjType;

    private Long objId;

    private Map<Long, Integer> menuMap;

    private SysDataAuthDTO sysDataAuth;

    public String getMenuAuthorityObjType() {
        return menuAuthorityObjType;
    }

    public void setMenuAuthorityObjType(String menuAuthorityObjType) {
        this.menuAuthorityObjType = menuAuthorityObjType;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
        this.objId = objId;
    }

    public Map<Long, Integer> getMenuMap() {
        return menuMap;
    }

    public void setMenuMap(Map<Long, Integer> menuMap) {
        this.menuMap = menuMap;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
