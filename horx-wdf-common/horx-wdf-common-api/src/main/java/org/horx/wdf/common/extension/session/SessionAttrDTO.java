package org.horx.wdf.common.extension.session;

import java.io.Serializable;

/**
 * 会话属性DTO。
 * @since 1.0
 */
public class SessionAttrDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long sessionId;

    private String attrKey;

    private String attrType;

    private String attrValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public void setAttrKey(String attrKey) {
        this.attrKey = attrKey;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
