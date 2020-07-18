package org.horx.common.collection;

/**
 * Key Value 数据结构。
 * @param <K> key类型。
 * @param <V> value类型。
 * @since 1.0
 */
public class KeyValue<K, V> {
    private K key;
    private V value;

    public KeyValue() {}

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取key。
     * @return
     */
    public K getKey() {
        return key;
    }

    /**
     * 设置key。
     * @param key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * 获取value。
     * @return
     */
    public V getValue() {
        return value;
    }

    /**
     * 设置value。
     * @param value
     */
    public void setValue(V value) {
        this.value = value;
    }
}
