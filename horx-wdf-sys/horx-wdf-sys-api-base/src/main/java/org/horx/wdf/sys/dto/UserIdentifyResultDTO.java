package org.horx.wdf.sys.dto;

import java.io.Serializable;
import java.util.Map;

public class UserIdentifyResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean needVcode;

    private Map<String, Object> extra;

    public Boolean getNeedVcode() {
        return needVcode;
    }

    public void setNeedVcode(Boolean needVcode) {
        this.needVcode = needVcode;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
