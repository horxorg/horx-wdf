package org.horx.wdf.common.exception;

import org.horx.wdf.common.enums.ErrorCodeEnum;

/**
 * 未识别用户异常，表示需要用户登录。
 * @since 1.0
 */
public class UnrecognizedUserException extends ErrorCodeException {
    public UnrecognizedUserException() {
        super("用户未登录", ErrorCodeEnum.A0230);
    }
}
