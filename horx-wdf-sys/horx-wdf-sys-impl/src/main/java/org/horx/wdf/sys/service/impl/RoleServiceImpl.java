package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.converter.MenuAuthorityConverter;
import org.horx.wdf.sys.converter.RoleConverter;
import org.horx.wdf.sys.dto.MenuAuthorityDTO;
import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.RoleWithAuthDTO;
import org.horx.wdf.sys.dto.wrapper.SaveMenuPermissionDTO;
import org.horx.wdf.sys.enums.MenuAuthorityObjTypeEnum;
import org.horx.wdf.sys.domain.Role;
import org.horx.wdf.sys.domain.MenuAuthority;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.manager.RoleManager;
import org.horx.wdf.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色Service实现。
 * @since 1.0
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    private MenuAuthorityConverter menuAuthorityConverter;


    @Override
    public RoleDTO getById(Long id) {
        Role role = roleManager.getById(id);
        RoleDTO roleDTO = roleConverter.toDto(role);
        return roleDTO;
    }

    @Override
    public RoleDTO getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth) {
        Role role = roleManager.getByIdAuthorized(id, sysDataAuth);
        RoleDTO roleDTO = roleConverter.toDto(role);
        return roleDTO;
    }

    @Override
    public RoleDTO getByIdUsable(Long id, SysDataAuthDTO sysDataAuth) {
        Role role = roleManager.getByIdUsable(id, sysDataAuth);
        RoleDTO roleDTO = roleConverter.toDto(role);
        return roleDTO;
    }

    @Override
    public PagingResult<RoleDTO> pagingQuery(PagingQuery<RoleQueryDTO> pagingQuery) {
        PagingResult<Role> pagingResult = roleManager.pagingQuery(pagingQuery.getQuery(), pagingQuery.getPagingParam());

        PagingResult<RoleDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<RoleDTO> dtoList = roleConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }

    @Override
    public PagingResult<RoleDTO> pagingQueryUsable(PagingQuery<RoleQueryDTO> pagingQuery) {
        PagingResult<Role> pagingResult = roleManager.pagingQueryUsable(pagingQuery.getQuery(), pagingQuery.getPagingParam());

        PagingResult<RoleDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<RoleDTO> dtoList = roleConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }

    @Override
    public Long create(RoleWithAuthDTO roleWithAuthDTO) {
        RoleDTO roleDTO = roleWithAuthDTO.getRole();
        Role role = roleConverter.fromDto(roleDTO);
        roleManager.create(role, roleWithAuthDTO.getSysDataAuth());
        return role.getId();
    }

    @Override
    public void modify(RoleWithAuthDTO roleWithAuthDTO) {
        RoleDTO roleDTO = roleWithAuthDTO.getRole();
        Role role = roleConverter.fromDto(roleDTO);
        roleManager.modify(role, roleWithAuthDTO.getSysDataAuth());
    }

    @Override
    public void saveMenuPermission(SaveMenuPermissionDTO saveMenuPermissionDTO) {
        MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum = MenuAuthorityObjTypeEnum.getByCode(saveMenuPermissionDTO.getMenuAuthorityObjType());
        roleManager.saveMenuPermission(menuAuthorityObjTypeEnum, saveMenuPermissionDTO.getObjId(),
                saveMenuPermissionDTO.getMenuMap(), saveMenuPermissionDTO.getSysDataAuth());
    }

    @Override
    public void remove(BatchWithSysAuthDTO batchWithAuth) {
        roleManager.remove(batchWithAuth.getIds(), batchWithAuth.getSysDataAuth());
    }

    @Override
    public List<MenuAuthorityDTO> queryForMenu(Long[] roleIds, String menuAuthorityObjType) {
        MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum = MenuAuthorityObjTypeEnum.getByCode(menuAuthorityObjType);
        List<MenuAuthority> list = roleManager.queryForMenu(roleIds, menuAuthorityObjTypeEnum);
        List<MenuAuthorityDTO> dtoList = menuAuthorityConverter.toDtoList(list);
        return dtoList;
    }
}
