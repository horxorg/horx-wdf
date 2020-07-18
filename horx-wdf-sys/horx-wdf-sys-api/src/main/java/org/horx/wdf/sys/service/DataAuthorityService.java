package org.horx.wdf.sys.service;

import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;

import java.util.List;

/**
 * 数据授权Service。
 * @since 1.0
 */
public interface DataAuthorityService {

    List<DataAuthorityDTO> queryRequestDataAuthority(Long userId, Long[] roleIds, Long dataPermissionId);

    List<DataAuthorityDTO> queryAdminRoleDataAuthority(Long[] roleIds, Long dataPermissionId);

    DataAuthorityDTO getById(Long id);

    DataAuthorityDTO getByObj(Long dataPermissionId, String objType, Long objId);

    Long create(DataAuthorityDTO dataAuthorityDTO);

    void modify(DataAuthorityDTO dataAuthorityDTO);

    List<DataAuthorityDetailDTO> queryDetailByAuthorityIds(Long[] authorityIds);

    boolean validate(Long[] authorityIds, String value);


}
