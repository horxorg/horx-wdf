package org.horx.wdf.common.exception;

import org.horx.wdf.common.entity.ErrorCode;

/**
 * 第三方服务异常，比如中间件异常。
 * @since 1.0
 */
public class ThirdPartyServiceException extends ErrorCodeException {

    public ThirdPartyServiceException() {
        super();
    }

    public ThirdPartyServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ThirdPartyServiceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ThirdPartyServiceException(String message, ErrorCode errorCode, Throwable throwable) {
        super(message, errorCode, throwable);
    }

    public ThirdPartyServiceException(String message, String code, String msgKey) {
        super(message, code, msgKey);
    }

    public ThirdPartyServiceException(String message, String code, String msgKey, Throwable throwable) {
        super(message, code, msgKey, throwable);
    }
}
