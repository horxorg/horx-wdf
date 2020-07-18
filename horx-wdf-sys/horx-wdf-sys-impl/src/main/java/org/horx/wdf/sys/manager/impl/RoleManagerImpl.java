package org.horx.wdf.sys.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.enums.OperationTypeEnum;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.domain.MenuAuthority;
import org.horx.wdf.sys.domain.Role;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.enums.MenuAuthorityObjTypeEnum;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.dataauth.DataValidationTool;
import org.horx.wdf.sys.manager.RoleManager;
import org.horx.wdf.sys.mapper.MenuAuthorityMapper;
import org.horx.wdf.sys.mapper.RoleMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色Manager实现。
 * @since 1.0
 */
@Component("roleManager")
public class RoleManagerImpl implements RoleManager {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuAuthorityMapper menuAuthorityMapper;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private DataValidationTool dataValidationTool;

    @Override
    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Role getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth) {
        return roleMapper.selectByIdAuthorized(id, sysDataAuth);
    }

    @Override
    public Role getByIdUsable(Long id, SysDataAuthDTO sysDataAuth) {
        return roleMapper.selectByIdUsable(id, sysDataAuth);
    }


    @Override
    @Transactional(readOnly = true)
    public PagingResult<Role> pagingQuery(RoleQueryDTO roleQuery, PagingParam pagingParam) {
        return roleMapper.pagingSelect(roleQuery, new PagingRowBounds(pagingParam));
    }


    @Override
    @Transactional(readOnly = true)
    public PagingResult<Role> pagingQueryUsable(RoleQueryDTO roleQuery, PagingParam pagingParam) {
        return roleMapper.pagingSelectUsable(roleQuery, new PagingRowBounds(pagingParam));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.role}", paramIndex = {0})
    public void create(Role role, SysDataAuthDTO sysDataAuth) {
        validate(role, sysDataAuth);
        if (StringUtils.isEmpty(role.getCode())) {
            role.setDelRid(-System.nanoTime());
        }

        roleMapper.insert(role);

        if (StringUtils.isEmpty(role.getCode())) {
            Role roleModify = new Role();
            roleModify.setId(role.getId());
            roleModify.setDelRid(role.getId());
            roleMapper.update(roleModify);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.role}", paramIndex = {0})
    public void modify(Role role, SysDataAuthDTO sysDataAuth) {
        Role old = getByIdAuthorized(role.getId(), sysDataAuth);
        if (old == null) {
            throw new PermissionDeniedException();
        }
        if (old.getOrgId() == null && role.getOrgId() != null ||
                old.getOrgId() != null && !old.getOrgId().equals(role.getOrgId())) {
            validate(role, sysDataAuth);
        }

        if (StringUtils.isEmpty(role.getCode())) {
            role.setDelRid(role.getId());
        } else {
            role.setDelRid(0L);
        }
        roleMapper.update(role);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.role}",
            operationType = OperationTypeEnum.MODIFY, desc = "${sys.menu.permissions}", paramIndex = {0, 1, 2})
    public void saveMenuPermission(MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum,
                                   Long objId, Map<Long, Integer> menuMap, SysDataAuthDTO sysDataAuth) {
        Role role = getByIdAuthorized(objId, sysDataAuth);
        if (role == null) {
            throw new PermissionDeniedException();
        }

        List<MenuAuthority> oldList = menuAuthorityMapper.selectByRoleId(new Long[]{objId},
                menuAuthorityObjTypeEnum.getCode());

        if (oldList != null && oldList.size() > 0) {
            List<Long> ids = new ArrayList<>();
            for (MenuAuthority item : oldList) {
                if (menuMap == null || !menuMap.containsKey(item.getMenuId())) {
                    ids.add(item.getId());
                } else {
                    Integer checkedType = menuMap.get(item.getMenuId());
                    if (!item.getCheckedType().equals(checkedType)) {
                        item.setCheckedType(checkedType);
                        menuAuthorityMapper.update(item);
                    }
                }
            }

            if (ids.size() > 0) {
                for (Long id : ids) {
                    menuAuthorityMapper.logicalDelete(id);
                }
            }
        }

        if (menuMap != null) {
            for (Map.Entry<Long, Integer> entry : menuMap.entrySet()) {
                Long menuId = entry.getKey();
                Integer checkedType = entry.getValue();
                if (menuId == null || checkedType == null) {
                    continue;
                }
                boolean exists = false;
                if (oldList != null && oldList.size() > 0) {
                    for (MenuAuthority item : oldList) {
                        if (item.getMenuId().equals(menuId)) {
                            exists = true;
                            break;
                        }
                    }
                }

                if (!exists) {
                    MenuAuthority menuAuthority = new MenuAuthority();
                    menuAuthority.setObjType(menuAuthorityObjTypeEnum.getCode());
                    menuAuthority.setObjId(objId);
                    menuAuthority.setMenuId(menuId);
                    menuAuthority.setCheckedType(checkedType);
                    menuAuthorityMapper.insert(menuAuthority);
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.role}", paramIndex = {0})
    public void remove(Long[] ids, SysDataAuthDTO sysDataAuth) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            Role old = getByIdAuthorized(id, sysDataAuth);
            if (old == null) {
                throw new PermissionDeniedException();
            }

            roleMapper.logicalDelete(id);
        }
    }

    @Override
    public List<MenuAuthority> queryForMenu(Long[] roleIds, MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum) {
        return menuAuthorityMapper.selectByRoleId(roleIds, menuAuthorityObjTypeEnum.getCode());
    }

    private void validate(Role role, SysDataAuthDTO sysDataAuth) {
        if (role.getOrgId() == null) {
            if (sysContextHolder.getUserOrgId() == null) {
                return;
            } else {
                throw new PermissionDeniedException();
            }
        }
        boolean authorized = dataValidationTool.validate(sysDataAuth.getOrgAuth(), role.getOrgId());
        if (!authorized) {
            throw new PermissionDeniedException();
        }
    }
}
