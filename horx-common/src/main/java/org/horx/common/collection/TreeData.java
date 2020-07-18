package org.horx.common.collection;

import java.util.List;

/**
 * 可序列化的树形数据。
 * @param <T> 节点数据类型。
 * @since 1.0
 */
public interface TreeData<T> {

    /**
     * 获取孩子节点。
     * @return
     */
    List<T> getChildren();

    /**
     * 设置孩子节点。
     * @param children
     */
    void setChildren(List<T> children);
}
