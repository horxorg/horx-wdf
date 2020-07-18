package org.horx.wdf.common.entity;

import org.horx.wdf.common.enums.ErrorCodeEnum;

import java.io.Serializable;

/**
 * 结果封装类。
 * @param <T> 结果对象类型。
 * @since 1.0
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功。
     */
    protected boolean success = true;
    /**
     * 结果代码。
     */
    protected String code = ErrorCodeEnum.OK.getCode();
    /**
     * 结果消息。
     */
    protected String msg;
    /**
     * 结果对象。
     */
    protected T data;

    public Result() {}

    /**
     * 构造方法。
     * @param data 结果对象。
     */
    public Result(T data) {
        this.data = data;
    }

    /**
     * 构造方法。
     * @param code 结果代码。
     * @param msg 结果消息。
     */
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = ErrorCodeEnum.OK.getCode().equals(code);
    }

    /**
     * 是否成功。
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 获取结果代码。
     * @return 结果代码。
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置结果代码。
     * @param code 结果代码。
     */
    public void setCode(String code) {
        this.code = code;
        this.success = ErrorCodeEnum.OK.getCode().equals(code);
    }

    /**
     * 获取结果消息。
     * @return 结果消息。
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置结果消息。
     * @param msg 结果消息。
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取结果对象。
     * @return 结果对象。
     */
    public T getData() {
        return data;
    }

    /**
     * 设置结果对象。
     * @param data 结果对象。
     */
    public void setData(T data) {
        this.data = data;
    }
}
