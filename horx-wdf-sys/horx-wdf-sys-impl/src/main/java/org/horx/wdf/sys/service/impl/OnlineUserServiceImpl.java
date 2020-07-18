package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.sys.converter.OnlineUserConverter;
import org.horx.wdf.sys.domain.OnlineUser;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.horx.wdf.sys.dto.UserDTO;
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
    public PagingResult<OnlineUserDTO> pagingQuery(PagingQuery<OnlineUserQueryDTO> pagingQuery) {
        PagingResult<OnlineUser> pagingResult = sessionManager.pagingQueryOnlineUser(pagingQuery.getQuery(), pagingQuery.getPagingParam());

        PagingResult<OnlineUserDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<OnlineUserDTO> dtoList = onlineUserConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }

    @Override
    public String[] offlineCheck(BatchWithSysAuthDTO batchWithAuth) {
        return sessionManager.offlineCheck(batchWithAuth.getIds(), batchWithAuth.getSysDataAuth());
    }
}
