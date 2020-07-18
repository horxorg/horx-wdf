package org.horx.wdf.sys.extension.datalog;

import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 数据操作日志Handler配置。
 * @since 1.0
 */
public class DataLogConfigHandler implements DataLogHandler {

    private List<DataLogHandler> dataLogHandlerList;

    public List<DataLogHandler> getDataLogHandlerList() {
        return dataLogHandlerList;
    }

    public void setDataLogHandlerList(List<DataLogHandler> dataLogHandlerList) {
        this.dataLogHandlerList = dataLogHandlerList;
    }

    @Override
    public void handle(DataOperationLogDTO dataLog) {
        if (CollectionUtils.isEmpty(dataLogHandlerList)) {
            return;
        }

        for (DataLogHandler datasLogHandler : dataLogHandlerList) {
            datasLogHandler.handle(dataLog);
        }
    }
}
