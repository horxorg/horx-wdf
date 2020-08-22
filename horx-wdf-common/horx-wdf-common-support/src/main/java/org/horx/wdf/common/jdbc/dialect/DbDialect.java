package org.horx.wdf.common.jdbc.dialect;

import org.horx.common.collection.KeyValue;
import org.horx.wdf.common.entity.Pageable;

import java.util.List;

/**
 * 数据库方言接口。
 * @since 1.0
 */
public interface DbDialect {

    /**
     * 是否使用序列。
     * @return
     */
    boolean useSequence();

    /**
     * 生成使用序列的sql。
     * @param sequenceName 序列名。
     * @return
     */
    String sequenceSql(String sequenceName);

    /**
     * 生成分页sql。
     * @param sql 原sql。
     * @param pageable 分页参数。
     * @return 分页sql。
     */
    PaginationSqlResult paginationSql(String sql, Pageable pageable);

    /**
     * 生成like条件值。
     * @param args
     * @return
     */
    String concatLike(String[] args);

    /**
     * 分页sql结果。
     */
    public static class PaginationSqlResult {
        private String paginationSql;

        private List<KeyValue<String, Object>> params;

        public String getPaginationSql() {
            return paginationSql;
        }

        public void setPaginationSql(String paginationSql) {
            this.paginationSql = paginationSql;
        }

        public List<KeyValue<String, Object>> getParams() {
            return params;
        }

        public void setParams(List<KeyValue<String, Object>> params) {
            this.params = params;
        }
    }
}
