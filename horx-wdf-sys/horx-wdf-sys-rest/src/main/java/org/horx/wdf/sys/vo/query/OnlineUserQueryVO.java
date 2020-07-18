package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.extension.value.annotation.DataAuth;

import java.io.Serializable;

/**
 * 在线用户日志查询条件VO。
 * @since 1.0
 */
public class OnlineUserQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String name;

    @RequestParameter()
    private String username;

    @RequestParameter()
    private Long[] orgIds;

    @DataAuth
    private SysDataAuthDTO sysDataAuth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long[] getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(Long[] orgIds) {
        this.orgIds = orgIds;
    }

    public SysDataAuthDTO getSysDataAuth() {
        return sysDataAuth;
    }

    public void setSysDataAuth(SysDataAuthDTO sysDataAuth) {
        this.sysDataAuth = sysDataAuth;
    }
}
