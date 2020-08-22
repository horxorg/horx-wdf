package org.horx.wdf.sys.manager;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.domain.MenuAuthority;
import org.horx.wdf.sys.domain.Role;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.enums.MenuAuthorityObjTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 角色Manager。
 * @since 1.0
 */
public interface RoleManager {
    Role getById(Long id);

    Role getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth);

    Role getByIdUsable(Long id, SysDataAuthDTO sysDataAuth);

    PaginationResult<Role> paginationQuery(RoleQueryDTO roleQuery, PaginationParam paginationParam);

    PaginationResult<Role> paginationQueryUsable(RoleQueryDTO roleQuery, PaginationParam paginationParam);

    void create(Role role, SysDataAuthDTO sysDataAuth);

    void modify(Role role, SysDataAuthDTO sysDataAuth);

    void saveMenuPermission(MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum, Long objId,
                            Map<Long, Integer> menuMap, SysDataAuthDTO sysDataAuth);

    void remove(Long[] ids, SysDataAuthDTO sysDataAuth);

    List<MenuAuthority> queryForMenu(Long[] roleIds, MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum);
}
