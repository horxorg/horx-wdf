package org.horx.wdf.sys.exception;

import org.horx.wdf.common.exception.ErrorCodeException;
import org.horx.wdf.common.enums.ErrorCodeEnum;

/**
 * 非授权访问异常。
 * @since 1.0
 */
public class PermissionDeniedException extends ErrorCodeException {
    public PermissionDeniedException() {
        super("未授权的访问", ErrorCodeEnum.A0300);
    }
}
