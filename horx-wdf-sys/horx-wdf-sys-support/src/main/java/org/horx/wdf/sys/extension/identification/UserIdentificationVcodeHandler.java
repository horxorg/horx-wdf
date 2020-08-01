package org.horx.wdf.sys.extension.identification;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserIdentifyResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户认证验证码Handler。
 * @since 1.0.1
 */
public class UserIdentificationVcodeHandler implements UserIdentificationHandler {

    private static final String ERROR_TIMES_KEY = "pwdErrorTimes";
    private static final String NEED_VCODE_KEY = "needVcode";
    private static final String VCODE_KEY = "vcode";

    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public boolean beforeIdentify(UserIdentifyDTO userIdentifyDTO, Result<UserIdentifyResultDTO> result) {
        if (isNeedVcode()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String vcode = request.getParameter("vcode");
            if (StringUtils.isEmpty(vcode)) {
                errorVcode(result);
                return false;
            }

            Object vcodeReservedObj = sessionHandler.getSessionAttr(VCODE_KEY);
            if (vcodeReservedObj == null) {
                result.setCode(ErrorCodeEnum.A0240.getCode());
                result.setMsg(msgTool.getMsg(ErrorCodeEnum.A0240.getMsgKey()));
                setNeedVcode(result);
                return false;
            }

            String[] vcodeReservedArr = vcodeReservedObj.toString().split(",");
            long vcodeCreateTime = Long.valueOf(vcodeReservedArr[1]);
            if (sysConfig.getVcodeTimeout() > 0 && System.currentTimeMillis() - vcodeCreateTime > sysConfig.getVcodeTimeout()) {
                result.setCode(ErrorCodeEnum.A0240.getCode());
                result.setMsg(msgTool.getMsg("sys.vcode.timeout"));
                setNeedVcode(result);
                sessionHandler.removeSessionAttr(VCODE_KEY);
                return false;
            }

            if (!vcode.equalsIgnoreCase(vcodeReservedArr[0])) {
                errorVcode(result);
                return false;
            }

            sessionHandler.removeSessionAttr(VCODE_KEY);
        }

        return true;
    }

    @Override
    public boolean afterIdentify(UserDTO user, Result<UserIdentifyResultDTO> result) {
        int vcodeAllowedErrorTimes = sysConfig.getVcodeAllowedErrorTimes();
        if (vcodeAllowedErrorTimes <= 0) {
            return true;
        }

        Object obj = sessionHandler.getSessionAttr(ERROR_TIMES_KEY);
        if (user == null) {
            int errorTimes = (obj == null) ? 1 : (Integer) obj + 1;
            if (errorTimes > sysConfig.getVcodeAllowedErrorTimes()) {
                setNeedVcode(result);
                sessionHandler.setSessionAttr(NEED_VCODE_KEY, true);
            }
            sessionHandler.setSessionAttr(ERROR_TIMES_KEY, errorTimes);
        } else if (obj != null) {
            sessionHandler.removeSessionAttr(ERROR_TIMES_KEY);
            sessionHandler.removeSessionAttr(NEED_VCODE_KEY);
        }

        return true;
    }

    private boolean isNeedVcode() {
        int vcodeAllowedErrorTimes = sysConfig.getVcodeAllowedErrorTimes();
        if (vcodeAllowedErrorTimes == 0) {
            return true;
        } else if (vcodeAllowedErrorTimes > 0) {
            Object obj = sessionHandler.getSessionAttr(NEED_VCODE_KEY);
            Boolean needVcode = (obj == null) ? false : (Boolean)obj;
            return needVcode;
        }
        return false;
    }

    private void errorVcode(Result<UserIdentifyResultDTO> result) {
        result.setCode(ErrorCodeEnum.A0240.getCode());
        result.setMsg(msgTool.getMsg(ErrorCodeEnum.A0240.getMsgKey()));
        setNeedVcode(result);
        sessionHandler.removeSessionAttr(VCODE_KEY);
    }

    private void setNeedVcode(Result<UserIdentifyResultDTO> result) {
        result.getData().setNeedVcode(true);
    }
}
