package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
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
    public PaginationResult<RoleDTO> paginationQuery(PaginationQuery<RoleQueryDTO> paginationQuery) {
        PaginationResult<Role> paginationResult = roleManager.paginationQuery(paginationQuery.getQuery(), paginationQuery.getPaginationParam());

        PaginationResult<RoleDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<RoleDTO> dtoList = roleConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
    }

    @Override
    public PaginationResult<RoleDTO> paginationQueryUsable(PaginationQuery<RoleQueryDTO> paginationQuery) {
        PaginationResult<Role> paginationResult = roleManager.paginationQueryUsable(paginationQuery.getQuery(), paginationQuery.getPaginationParam());

        PaginationResult<RoleDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<RoleDTO> dtoList = roleConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
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
