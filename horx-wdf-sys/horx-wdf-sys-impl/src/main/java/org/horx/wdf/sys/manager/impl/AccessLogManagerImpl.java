package org.horx.wdf.sys.manager.impl;

import org.horx.common.utils.DateUtils;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.domain.AccessLog;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;
import org.horx.wdf.sys.manager.AccessLogManager;
import org.horx.wdf.sys.mapper.AccessLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 访问日志Manager实现。
 * @since 1.0
 */
@Component("accessLogManager")
public class AccessLogManagerImpl implements AccessLogManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogManagerImpl.class);

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public Long create(AccessLog accessLog) {
        accessLogMapper.insert(accessLog);
        return accessLog.getId();
    }

    @Override
    public void removeHistory() {
        int accessLogReserveDays = sysConfig.getAccessLogReserveDays();
        if (accessLogReserveDays < 0) {
            return;
        }

        Date dateTime = new Date();
        if (accessLogReserveDays > 0) {
            dateTime = DateUtils.addDay(dateTime, -accessLogReserveDays);
        }

        int rows = accessLogMapper.deleteHistory(dateTime);
        if (rows > 0) {
            LOGGER.info("删除{}前访问日志{}条", DateUtils.format(dateTime, "yyyy-MM-dd HH:mm:ss"), rows);
        }
    }

    @Override
    public AccessLog getById(Long id) {
        return accessLogMapper.selectById(id);
    }

    @Override
    public PaginationResult<AccessLog> paginationQuery(AccessLogQueryDTO accessLogQuery, PaginationParam paginationParam) {
        return accessLogMapper.paginationSelect(accessLogQuery, new PaginationRowBounds(paginationParam));
    }
}
