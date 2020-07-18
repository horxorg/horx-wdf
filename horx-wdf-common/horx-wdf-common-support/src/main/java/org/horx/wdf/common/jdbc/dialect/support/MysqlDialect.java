package org.horx.wdf.common.jdbc.dialect.support;

import org.horx.common.collection.KeyValue;
import org.horx.wdf.common.entity.Pageable;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.jdbc.dialect.DbDialect;

import java.util.ArrayList;
import java.util.List;

/**
 * Mysql方言。
 * @since 1.0
 */
public class MysqlDialect implements DbDialect {
    @Override
    public boolean useSequence() {
        return false;
    }

    @Override
    public String sequenceSql(String sequenceName) {
        return null;
    }

    @Override
    public PagingSqlResult pagingSql(String sql, Pageable pageable) {
        Integer start = pageable.getStart();
        if (start == null) {
            return null;
        }
        StringBuilder pagingSql = new StringBuilder(sql.length() + 10);
        pagingSql.append(sql);
        pagingSql.append(" limit ?,?");

        PagingSqlResult result = new PagingSqlResult();
        result.setPagingSql(pagingSql.toString());

        List<KeyValue<String, Object>> params = new ArrayList<>(2);

        KeyValue<String, Object> offset = new KeyValue<String, Object>("offset", start - 1);
        KeyValue<String, Object> limit = new KeyValue<String, Object>("limit", pageable.getPageSize());

        params.add(offset);
        params.add(limit);
        result.setParams(params);

        return result;
    }

    @Override
    public String concatLike(String[] args) {
        StringBuilder builder = new StringBuilder("CONCAT(");
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append(args[i]);
        }
        builder.append(')');
        return builder.toString();
    }
}
