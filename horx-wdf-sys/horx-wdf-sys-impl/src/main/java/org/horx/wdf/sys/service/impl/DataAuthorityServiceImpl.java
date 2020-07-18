package org.horx.wdf.sys.service.impl;

import org.horx.wdf.sys.converter.DataAuthorityConverter;
import org.horx.wdf.sys.converter.DataAuthorityDetailConverter;
import org.horx.wdf.sys.domain.DataAuthority;
import org.horx.wdf.sys.domain.DataAuthorityDetail;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.manager.DataAuthorityManager;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据授权Service实现。
 * @since 1.0
 */
@Service("dataAuthorityService")
public class DataAuthorityServiceImpl implements DataAuthorityService {

    @Autowired
    private DataAuthorityManager dataAuthorityManager;

    @Autowired
    private DataAuthorityConverter dataAuthorityConverter;

    @Autowired
    private DataAuthorityDetailConverter dataAuthorityDetailConverter;

    @Override
    public List<DataAuthorityDTO> queryRequestDataAuthority(Long userId, Long[] roleIds, Long dataPermissionId) {
        List<DataAuthority> dataAuthorityList = dataAuthorityManager.queryRequestDataAuthority(userId, roleIds, dataPermissionId);
        List<DataAuthorityDTO> dtoList = dataAuthorityConverter.toDtoList(dataAuthorityList);
        return dtoList;
    }

    @Override
    public List<DataAuthorityDTO> queryAdminRoleDataAuthority(Long[] roleIds, Long dataPermissionId) {
        List<DataAuthority> dataAuthorityList = dataAuthorityManager.queryAdminRoleDataAuthority(roleIds, dataPermissionId);
        List<DataAuthorityDTO> dtoList = dataAuthorityConverter.toDtoList(dataAuthorityList);
        return dtoList;
    }

    @Override
    public DataAuthorityDTO getById(Long id) {
        DataAuthority dataAuthority = dataAuthorityManager.getById(id);
        DataAuthorityDTO dto = dataAuthorityConverter.toDto(dataAuthority);
        return dto;
    }

    @Override
    public DataAuthorityDTO getByObj(Long dataPermissionId, String objType, Long objId) {
        DataAuthority dataAuthority = dataAuthorityManager.getByObj(dataPermissionId, objType, objId);
        DataAuthorityDTO dto = dataAuthorityConverter.toDto(dataAuthority);
        return dto;
    }

    @Override
    public Long create(DataAuthorityDTO dataAuthorityDTO) {
        DataAuthority dataAuthority = dataAuthorityConverter.fromDto(dataAuthorityDTO);
        dataAuthorityManager.create(dataAuthority);
        return dataAuthority.getId();
    }

    @Override
    public void modify(DataAuthorityDTO dataAuthorityDTO) {
        DataAuthority dataAuthority = dataAuthorityConverter.fromDto(dataAuthorityDTO);
        dataAuthorityManager.modify(dataAuthority);
    }

    @Override
    public List<DataAuthorityDetailDTO> queryDetailByAuthorityIds(Long[] authorityIds) {
        List<DataAuthorityDetail> list = dataAuthorityManager.queryDetailByAuthorityIds(authorityIds);
        List<DataAuthorityDetailDTO> dtoList = dataAuthorityDetailConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public boolean validate(Long[] authorityIds, String value) {
        return dataAuthorityManager.validate(authorityIds, value);
    }
}
