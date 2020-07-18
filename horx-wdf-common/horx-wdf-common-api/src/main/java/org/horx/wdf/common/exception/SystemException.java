package org.horx.wdf.common.exception;

import org.horx.wdf.common.entity.ErrorCode;

/**
 * 系统异常，比如服务端执行出错。
 * @since 1.0
 */
public class SystemException extends ErrorCodeException {

    public SystemException() {
        super();
    }

    public SystemException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SystemException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SystemException(String message, ErrorCode errorCode, Throwable throwable) {
        super(message, errorCode, throwable);
    }

    public SystemException(String message, String code, String msgKey) {
        super(message, code, msgKey);
    }

    public SystemException(String message, String code, String msgKey, Throwable throwable) {
        super(message, code, msgKey, throwable);
    }
}
