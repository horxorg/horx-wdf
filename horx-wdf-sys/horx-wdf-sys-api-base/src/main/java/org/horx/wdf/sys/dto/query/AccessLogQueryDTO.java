package org.horx.wdf.sys.dto.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 访问日志查询条件DTO。
 * @since 1.0
 */
public class AccessLogQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String searchKey;

    private Date startTime;

    private Date endTime;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
