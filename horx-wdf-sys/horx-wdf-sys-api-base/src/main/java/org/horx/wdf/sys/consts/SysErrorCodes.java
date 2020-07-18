package org.horx.wdf.sys.consts;

import org.horx.wdf.common.entity.ErrorCode;
import org.horx.wdf.common.entity.ErrorCodeEntity;
import org.horx.wdf.common.enums.ErrorCodeEnum;

/**
 * 常用结果。
 * @since 1.0
 */
public class SysErrorCodes {

    public static final ErrorCode USER_NOT_EXIST = ErrorCodeEnum.A0201;

    public static final ErrorCode USER_DISABLED = ErrorCodeEnum.A0202;

    public static final ErrorCode LOGIN_PWD_INVALID = ErrorCodeEnum.A0210;

    public static final ErrorCode OLD_PWD_INVALID = new ErrorCodeEntity(ErrorCodeEnum.A0210.getCode(), "sys.old.pwd.invalid");

    public static final ErrorCode LOGIN_LOCKED = new ErrorCodeEntity(ErrorCodeEnum.A0202.getCode(), "sys.login.locked");
}
