package org.horx.wdf.common.exception;

import org.horx.wdf.common.entity.ErrorCode;

/**
 * 因客户端产生的异常，比如客户端输入非法数据。
 * @since 1.0
 */
public class ClientException extends ErrorCodeException {

    public ClientException() {
        super();
    }

    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ClientException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ClientException(String message, ErrorCode errorCode, Throwable throwable) {
        super(message, errorCode, throwable);
    }

    public ClientException(String message, String code, String msgKey) {
        super(message, code, msgKey);
    }

    public ClientException(String message, String code, String msgKey, Throwable throwable) {
        super(message, code, msgKey, throwable);
    }
}
