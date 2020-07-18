package org.horx.wdf.common.entity;

import java.io.Serializable;

/**
 * 错误码实体类。
 * @since 1.0
 */
public class ErrorCodeEntity implements ErrorCode, Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msgKey;
    private String comment;
    private Integer level;
    private String parentCode;

    /**
     * 构造方法。
     * @param code 错误码。
     * @param msgKey 结果消息所对应的消息key。
     */
    public ErrorCodeEntity(String code, String msgKey) {
        this.code = code;
        this.msgKey = msgKey;
    }

    /**
     * 构造方法。
     * @param code 错误码。
     * @param msgKey 结果消息所对应的消息key。
     * @param comment 注释。
     * @param level 所在层级。
     * @param parentCode 父错误码。
     */
    public ErrorCodeEntity(String code, String msgKey, String comment, Integer level, String parentCode) {
        this.code = code;
        this.msgKey = msgKey;
        this.comment = comment;
        this.level = level;
        this.parentCode = parentCode;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsgKey() {
        return msgKey;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public String getParentCode() {
        return parentCode;
    }
}
