package org.horx.wdf.common.type.nulls;

import java.io.Serializable;
import java.util.Date;

/**
 * 空Date值。
 * @since 1.0
 */
public class NullDate extends Date implements NullValue {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "null";
    }
}
