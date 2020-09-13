package org.horx.wdf.sys.manager.impl;

import org.horx.common.utils.DateUtils;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.domain.DataOperationLog;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;
import org.horx.wdf.sys.manager.DataOperationLogManager;
import org.horx.wdf.sys.mapper.DataOperationLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 数据操作日志Manager实现。
 * @since 1.0
 */
@Component("dataOperationLogManager")
public class DataOperationLogManagerImpl implements DataOperationLogManager {
    private static final Logger logger = LoggerFactory.getLogger(DataOperationLogManagerImpl.class);

    @Autowired
    private DataOperationLogMapper dataOperationLogMapper;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public Long create(DataOperationLog dataOperationLog) {
        dataOperationLogMapper.insert(dataOperationLog);
        return dataOperationLog.getId();
    }

    @Override
    public void removeHistory() {
        int dataLogReserveDays = sysConfig.getDataLogReserveDays();
        if (dataLogReserveDays < 0) {
            return;
        }

        Date dateTime = new Date();
        if (dataLogReserveDays > 0) {
            dateTime = DateUtils.addDay(dateTime, -dataLogReserveDays);
        }

        int rows = dataOperationLogMapper.deleteHistory(dateTime);
        if (rows > 0) {
            logger.info("删除{}前数据操作日志{}条", DateUtils.format(dateTime, "yyyy-MM-dd HH:mm:ss"), rows);
        }
    }

    @Override
    public DataOperationLog getById(Long id) {
        return dataOperationLogMapper.selectById(id);
    }

    @Override
    public PaginationResult<DataOperationLog> paginationQuery(DataOperationLogQueryDTO dataOperationLogQuery, PaginationParam paginationParam) {
        return dataOperationLogMapper.paginationSelect(dataOperationLogQuery, new PaginationRowBounds(paginationParam));
    }
}
