package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;

/**
 * 在线用户Service。
 * @since 1.0
 */
public interface OnlineUserService {

    PaginationResult<OnlineUserDTO> paginationQuery(PaginationQuery<OnlineUserQueryDTO> paginationQuery);

    /**
     * 用户下线检查。
     * @param batchWithAuth
     * @return 可下线的sessionKey数组。
     */
    String[] offlineCheck(BatchWithSysAuthDTO batchWithAuth);
}
