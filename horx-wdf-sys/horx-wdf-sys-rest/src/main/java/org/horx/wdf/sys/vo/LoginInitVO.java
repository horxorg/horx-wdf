package org.horx.wdf.sys.vo;

import java.io.Serializable;

public class LoginInitVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean needVcode;

    private String vcodeUrl;

    private String returnUrl;

    private String identifyUrl;

    public Boolean getNeedVcode() {
        return needVcode;
    }

    public void setNeedVcode(Boolean needVcode) {
        this.needVcode = needVcode;
    }

    public String getVcodeUrl() {
        return vcodeUrl;
    }

    public void setVcodeUrl(String vcodeUrl) {
        this.vcodeUrl = vcodeUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getIdentifyUrl() {
        return identifyUrl;
    }

    public void setIdentifyUrl(String identifyUrl) {
        this.identifyUrl = identifyUrl;
    }
}
