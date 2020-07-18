package org.horx.wdf.common.exception;

import org.horx.wdf.common.entity.ErrorCode;

/**
 * 错误码异常。
 * @since 1.0
 */
public class ErrorCodeException extends RuntimeException {

    protected String code;

    protected String msgKey;

    public ErrorCodeException() {
        super();
    }

    public ErrorCodeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ErrorCodeException(String message, ErrorCode errorCode) {
        super(message);
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.msgKey = errorCode.getMsgKey();
        }
    }

    public ErrorCodeException(String message, ErrorCode errorCode, Throwable throwable) {
        super(message, throwable);
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.msgKey = errorCode.getMsgKey();
        }
    }

    public ErrorCodeException(String message, String code, String msgKey) {
        super(message);
        this.code = code;
        this.msgKey = msgKey;
    }

    public ErrorCodeException(String message, String code, String msgKey, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.msgKey = msgKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }
}
