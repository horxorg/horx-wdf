package org.horx.wdf.sys.manager;

import org.horx.wdf.sys.domain.DataAuthority;
import org.horx.wdf.sys.domain.DataAuthorityDetail;

import java.util.List;

/**
 * 数据授权Manager。
 * @since 1.0
 */
public interface DataAuthorityManager {

    List<DataAuthority> queryRequestDataAuthority(Long userId, Long[] roleIds, Long dataPermissionId);

    List<DataAuthority> queryAdminRoleDataAuthority(Long[] roleIds, Long dataPermissionId);

    DataAuthority getById(Long id);

    DataAuthority getByObj(Long dataPermissionId, String objType, Long objId);

    void create(DataAuthority dataAuthority);

    void modify(DataAuthority dataAuthority);

    List<DataAuthorityDetail> queryDetailByAuthorityIds(Long[] authorityIds);

    boolean validate(Long[] authorityIds, String value);
}
