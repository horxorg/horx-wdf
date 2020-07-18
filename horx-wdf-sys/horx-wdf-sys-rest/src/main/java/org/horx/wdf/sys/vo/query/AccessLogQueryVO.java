package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import java.io.Serializable;
import java.util.Date;

/**
 * 访问日志查询条件VO。
 * @since 1.0
 */
public class AccessLogQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @RequestParameter()
    private String searchKey;

    @RequestParameter()
    private Date startTime;

    @RequestParameter()
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
