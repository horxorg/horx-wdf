package org.horx.wdf.common.mybatis.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.horx.common.collection.KeyValue;
import org.horx.wdf.common.entity.SortItem;
import org.horx.wdf.common.entity.Sortable;
import org.horx.wdf.common.filed.sort.CustomSort;
import org.horx.wdf.common.filed.sort.CustomSortProvider;
import org.horx.wdf.common.enums.SortEnum;
import org.horx.wdf.common.jdbc.dialect.DbDialect;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.mybatis.config.PaginationList;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 分页拦截器。
 * @since 1.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PaginationInterceptor implements Interceptor {
    private final static Logger LOGGER = LoggerFactory.getLogger(PaginationInterceptor.class);

    private DbDialect dbDialect;
    private EntityExtension entityExtension;

    private CustomSortProvider customSortProvider = new CustomSortProvider();

    public void setDbDialect(DbDialect dbDialect) {
        this.dbDialect = dbDialect;
    }
    public void setEntityExtension(EntityExtension entityExtension) {
        this.entityExtension = entityExtension;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof StatementHandler) {
            return handleStatement(invocation);
        } else if (target instanceof ResultSetHandler) {
            return handleResultSet(invocation);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Object handleStatement(Invocation invocation) throws Throwable {
        MetaObject metaStatementHandler = MetaObjectUtils.getTargetMetaObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (mappedStatement.getSqlCommandType() != SqlCommandType.SELECT) {
            return invocation.proceed();
        }

        Object rowBoundsObj = metaStatementHandler.getValue("delegate.rowBounds");
        if (rowBoundsObj == null) {
            return invocation.proceed();
        }

        RowBounds rowBounds = (RowBounds)rowBoundsObj;
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            return invocation.proceed();
        }

        PaginationRowBounds paginationRowBounds = null;
        SortItem[] sortItems = null;
        if (rowBoundsObj instanceof PaginationRowBounds) {
            paginationRowBounds = (PaginationRowBounds) rowBoundsObj;
            sortItems = paginationRowBounds.getSortItems();
        } else if (rowBoundsObj instanceof Sortable) {
            Sortable sortable = (Sortable)rowBounds;
            sortItems = sortable.getSortItems();
        }

        if ((sortItems == null || sortItems.length == 0) && (paginationRowBounds == null || paginationRowBounds.getStart() == null)){
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");


        // 重设分页参数里的总页数等
        String sql = boundSql.getSql();
        Connection connection = (Connection) invocation.getArgs()[0];
        int total = getTotal(sql, connection, mappedStatement, boundSql);
        if (paginationRowBounds != null) {
            paginationRowBounds.setTotal(total);
        }

        // 重写sql
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        buildPaginationSql(mappedStatement, metaStatementHandler, boundSql, paginationRowBounds, sortItems, configuration);

        return invocation.proceed();
    }

    private Object handleResultSet(Invocation invocation) throws Throwable {
        ResultSetHandler resultSetHandler = (ResultSetHandler)invocation.getTarget();
        MetaObject metaobj = MetaObjectUtils.getMetaObject(resultSetHandler);
        Object rowBoundsObj = metaobj.getValue("rowBounds");
        Object result = null;
        if (rowBoundsObj != null && rowBoundsObj instanceof PaginationRowBounds) {
            PaginationRowBounds paginationRowBounds = (PaginationRowBounds)rowBoundsObj;
            List list = null;
            if (paginationRowBounds.getStart() != null && paginationRowBounds.getTotal() != null && paginationRowBounds.getStart() > paginationRowBounds.getTotal()) {
                list = new ArrayList(0);
            } else {
                list = (List)invocation.proceed();
            }

            PaginationList paginationList = new PaginationList(paginationRowBounds, list);
            result = paginationList;
        } else {
            result = invocation.proceed();
        }

        return result;
    }

    private int getTotal(String sql, Connection connection, MappedStatement mappedStatement,
                         BoundSql boundSql) throws Exception {
        // 记录总记录数
        String countSql = "select count(*) from (" + sql + ") t";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());

            List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
            if (parameterMappingList != null && parameterMappingList.size() > 0) {
                for (ParameterMapping parameterMapping : parameterMappingList) {
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object propertyValue = boundSql.getAdditionalParameter(propertyName);
                        countBS.setAdditionalParameter(propertyName, propertyValue);
                    } else {
                        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                        if (propertyName.startsWith("__frch_") && boundSql.hasAdditionalParameter(prop.getName())) {
                            Object propertyValue = boundSql.getAdditionalParameter(prop.getName());
                            countBS.setAdditionalParameter(propertyName, propertyValue);
                        }
                    }
                }
            }

            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

            return totalCount;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.error("Ignore this exception", e);
                }
            }

            if (countStmt != null) {
                try {
                    countStmt.close();
                } catch (SQLException e) {
                    LOGGER.error("Ignore this exception", e);
                }
            }
        }
    }

    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    private void buildPaginationSql(MappedStatement mappedStatement, MetaObject metaStatementHandler, BoundSql boundSql, PaginationRowBounds paginationRowBounds, SortItem[] sortItems, Configuration configuration) {
        String sql = boundSql.getSql();
        if (sortItems != null && sortItems.length > 0) {
            sql = genOrderSql(mappedStatement, sql, sortItems);
        }

        if (paginationRowBounds == null) {
            if (sortItems != null && sortItems.length > 0) {
                metaStatementHandler.setValue("delegate.boundSql.sql", sql);
            }
            return;
        }

        DbDialect.PaginationSqlResult paginationSqlResult = dbDialect.paginationSql(sql, paginationRowBounds);
        if (paginationSqlResult == null) {
            if (sortItems != null && sortItems.length > 0) {
                metaStatementHandler.setValue("delegate.boundSql.sql", sql);
            }
            return;
        }

        //重写分页sql
        metaStatementHandler.setValue("delegate.boundSql.sql", paginationSqlResult.getPaginationSql());

        List<KeyValue<String, Object>> params = paginationSqlResult.getParams();
        if (params != null && params.size() > 0) {
            MetaObject boundSqlMeta = MetaObjectUtils.getMetaObject(boundSql);
            List<ParameterMapping> paramList = new ArrayList<ParameterMapping>();
            paramList.addAll(boundSql.getParameterMappings());

            for (KeyValue<String, Object> entry : params) {
                ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, entry.getKey(), entry.getValue().getClass());
                paramList.add(builder.build());
                boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
            }

            boundSqlMeta.setValue("parameterMappings", paramList);
        }

        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
    }

    private String genOrderSql(MappedStatement mappedStatement, String sql, SortItem[] sortItems) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        if (entityClass == null) {
            return sql;
        }

        StringBuilder join = null;
        StringBuilder order = new StringBuilder();

        entityClass = entityExtension.getExtensionClass(entityClass);
        EntityMeta entityMeta = MetaUtils.getEntityInfo(entityClass);

        for (SortItem sortItem : sortItems) {
            EntityMeta.FieldMeta fieldMeta = entityMeta.getFieldMeta(sortItem.getField());
            if (fieldMeta == null) {
                continue;
            }

            Annotation customSortDef = fieldMeta.getCustomSortDef();
            CustomSort customSort = (customSortDef == null) ? null : customSortProvider.genCustomSort(fieldMeta);
            if (customSort == null) {
                if (order.length() > 0) {
                    order.append(',');
                }
                order.append(fieldMeta.getColumnName());
                if (SortEnum.DESC.name().equalsIgnoreCase(sortItem.getOrder())) {
                    order.append(" ").append("DESC");
                }
            } else {
                String joinSql = customSort.getJoin();
                if (StringUtils.isNotEmpty(joinSql)) {
                    if (join == null) {
                        join = new StringBuilder();
                    }
                    join.append(" ");
                    join.append(joinSql);
                }

                if (order.length() > 0) {
                    order.append(',');
                }

                if (SortEnum.DESC.name().equalsIgnoreCase(sortItem.getOrder())) {
                    String descSql = customSort.getDesc();
                    if (StringUtils.isEmpty(descSql)) {
                        order.append(fieldMeta.getColumnName()).append(" ").append("DESC");
                    } else {
                        order.append(descSql);
                    }
                } else {
                    String ascSql = customSort.getAsc();
                    if (StringUtils.isEmpty(ascSql)) {
                        order.append(fieldMeta.getColumnName());
                    } else {
                        order.append(ascSql);
                    }
                }
            }
        }

        StringBuilder newSql = new StringBuilder();
        if (join == null || join.length() == 0) {
            newSql.append(sql).append(" ORDER BY ").append(order);
        } else {
            newSql.append("SELECT T.* FROM (").append(sql).append(") T").append(join).append(" ORDER BY ").append(order);
        }

        return newSql.toString();
    }

    private Class<?> getEntityClass(MappedStatement mappedStatement) {
        List<ResultMap> rms = mappedStatement.getResultMaps();
        return (rms != null && rms.size() > 0) ? rms.get(0).getType() : null;
    }
}
