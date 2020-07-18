package org.horx.wdf.common.exception;

import org.horx.wdf.common.entity.ErrorCode;

/**
 * RPC调用异常。
 * @since 1.0
 */
public class RpcException extends ErrorCodeException {

    public RpcException() {
        super();
    }

    public RpcException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RpcException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public RpcException(String message, ErrorCode errorCode, Throwable throwable) {
        super(message, errorCode, throwable);
    }

    public RpcException(String message, String code, String msgKey) {
        super(message, code, msgKey);
    }

    public RpcException(String message, String code, String msgKey, Throwable throwable) {
        super(message, code, msgKey, throwable);
    }
}
