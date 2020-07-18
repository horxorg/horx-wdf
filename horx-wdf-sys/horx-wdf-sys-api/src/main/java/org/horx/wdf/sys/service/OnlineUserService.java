package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;

/**
 * 在线用户Service。
 * @since 1.0
 */
public interface OnlineUserService {

    PagingResult<OnlineUserDTO> pagingQuery(PagingQuery<OnlineUserQueryDTO> pagingQuery);

    /**
     * 用户下线检查。
     * @param batchWithAuth
     * @return 可下线的sessionKey数组。
     */
    String[] offlineCheck(BatchWithSysAuthDTO batchWithAuth);
}
