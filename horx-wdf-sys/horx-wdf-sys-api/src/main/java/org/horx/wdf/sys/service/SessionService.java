package org.horx.wdf.sys.service;

import org.horx.wdf.common.extension.session.CommonSessionService;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.UserDTO;

/**
 * 会话Service。
 * @since 1.0
 */
public interface SessionService extends CommonSessionService {

    UserDTO getUserById(Long userId);

    OrgDTO getOrgById(Long orgId);

    Long[] getRoleIdsByPermissionCode(Long userId, String permissionCode);
}
