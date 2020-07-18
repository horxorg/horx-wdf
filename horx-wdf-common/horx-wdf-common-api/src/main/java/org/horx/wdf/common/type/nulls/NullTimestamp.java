package org.horx.wdf.common.type.nulls;

import java.sql.Timestamp;

/**
 * 空Timestamp值。
 * @since 1.0
 */
public class NullTimestamp extends Timestamp implements NullValue {
    private static final long serialVersionUID = 1L;

    public NullTimestamp() {
        super(0L);
    }

    @Override
    public String toString() {
        return "null";
    }

}
