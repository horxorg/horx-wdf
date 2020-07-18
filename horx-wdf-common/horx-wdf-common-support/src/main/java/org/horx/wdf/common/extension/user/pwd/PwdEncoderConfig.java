package org.horx.wdf.common.extension.user.pwd;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.extension.user.CommonUser;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 密码Encoder配置。
 * @since 1.0
 */
public class PwdEncoderConfig implements PwdEncoder {

    private Map<String, PwdEncoder> pwdEncoderMap;

    private String currentPwdEncoderCode;

    private PwdEncoder currentPwdEncoder;

    public Map<String, PwdEncoder> getPwdEncoderMap() {
        return pwdEncoderMap;
    }

    public void setPwdEncoderMap(Map<String, PwdEncoder> pwdEncoderMap) {
        this.pwdEncoderMap = pwdEncoderMap;
    }

    public String getCurrentPwdEncoderCode() {
        return currentPwdEncoderCode;
    }

    public void setCurrentPwdEncoderCode(String currentPwdEncoderCode) {
        this.currentPwdEncoderCode = currentPwdEncoderCode;
    }

    public PwdEncoder getCurrentPwdEncoder() {
        return currentPwdEncoder;
    }

    @PostConstruct
    public void init() {
        currentPwdEncoder = pwdEncoderMap.get(currentPwdEncoderCode);
    }


    @Override
    public void modifyPwd(CommonUser user) {
        currentPwdEncoder.modifyPwd(user);
        user.setPwdEncodeType(currentPwdEncoderCode);
    }

    @Override
    public String encodePwd(CommonUser user) {
        PwdEncoder pwdEncoder = null;
        if (StringUtils.isEmpty(user.getPwdEncodeType())) {
            pwdEncoder = currentPwdEncoder;
        } else {
            pwdEncoder = pwdEncoderMap.get(user.getPwdEncodeType());
        }
        return pwdEncoder.encodePwd(user);
    }
}
