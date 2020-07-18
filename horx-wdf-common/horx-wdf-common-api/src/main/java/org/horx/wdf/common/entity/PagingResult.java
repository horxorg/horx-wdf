package org.horx.wdf.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果。
 * @param <T>
 */
public class PagingResult<T> extends Result<List<T>> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总条数。
     */
    protected Integer total;

    /**
     * 当前页数据。
     */
    protected List<T> data;

    public PagingResult() {}

    /**
     * 构造方法。
     * @param code 结果代码。
     * @param msg 结果消息。
     */
    public PagingResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取总条数。
     * @return 总条数。
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 设置总条数。
     * @param total 总条数。
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 获取当前页数据。
     * @return 当前页数据。
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置当前页数据。
     * @param data 当前页数据。
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 获取一个空的分页结果对象。
     * @return 空的分页结果对象。
     */
    public static PagingResult empty() {
        PagingResult result = new PagingResult();
        result.setTotal(0);
        result.setData(new ArrayList(0));
        return result;
    }

    /**
     * 复制分页结果对象。
     * @param result 源分页结果对象。
     * @return 复制后的分页结果对象。
     */
    public static PagingResult copy(PagingResult result) {
        PagingResult newResult = new PagingResult();
        newResult.setTotal(result.getTotal());
        return newResult;
    }
}
