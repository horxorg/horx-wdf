package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.converter.OnlineUserConverter;
import org.horx.wdf.sys.domain.OnlineUser;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.manager.SessionManager;
import org.horx.wdf.sys.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 在线用户Service实现。
 * @since 1.0
 */
@Service("onlineUserService")
public class OnlineUserServiceImpl implements OnlineUserService {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private OnlineUserConverter onlineUserConverter;

    @Override
    public PaginationResult<OnlineUserDTO> paginationQuery(PaginationQuery<OnlineUserQueryDTO> paginationQuery) {
        PaginationResult<OnlineUser> paginationResult = sessionManager.paginationQueryOnlineUser(paginationQuery.getQuery(), paginationQuery.getPaginationParam());

        PaginationResult<OnlineUserDTO> paginationResultDTO = PaginationResult.copy(paginationResult);
        List<OnlineUserDTO> dtoList = onlineUserConverter.toDtoList(paginationResult.getData());
        paginationResultDTO.setData(dtoList);
        return paginationResultDTO;
    }

    @Override
    public String[] offlineCheck(BatchWithSysAuthDTO batchWithAuth) {
        return sessionManager.offlineCheck(batchWithAuth.getIds(), batchWithAuth.getSysDataAuth());
    }
}
