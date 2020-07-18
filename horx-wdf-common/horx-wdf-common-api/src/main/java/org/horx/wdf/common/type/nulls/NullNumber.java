package org.horx.wdf.common.type.nulls;

/**
 * 空Number值。
 * @since 1.0
 */
public class NullNumber extends Number {
    private static final long serialVersionUID = 1L;

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0L;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0D;
    }

    @Override
    public String toString() {
        return "null";
    }
}
