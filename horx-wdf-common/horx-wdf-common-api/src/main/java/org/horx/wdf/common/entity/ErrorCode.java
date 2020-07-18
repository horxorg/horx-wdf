package org.horx.wdf.common.entity;

/**
 * 错误码接口。
 * @since 1.0
 */
public interface ErrorCode {

    /**
     * 获取错误码。
     * @return 错误码。
     */
    String getCode();

    /**
     * 获取结果消息所对应的消息key。
     * @return 结果消息所对应的消息key。
     */
    String getMsgKey();

    /**
     * 获取注释。
     * @return
     */
    String getComment();

    /**
     * 获取所在的层级。
     * @return
     */
    Integer getLevel();

    /**
     * 获取父编码。
     * @return
     */
    String getParentCode();
}
