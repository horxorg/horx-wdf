package org.horx.wdf.common.mybatis.interceptor;

import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.horx.wdf.common.jdbc.annotation.Sequence;
import org.horx.wdf.common.jdbc.dialect.DbDialect;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.common.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 主键生成器拦截器。
 * @since 1.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class KeyGeneratorInterceptor implements Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(KeyGeneratorInterceptor.class);

    private DbDialect dbDialect;

    public void setDbDialect(DbDialect dbDialect) {
        this.dbDialect = dbDialect;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaStatementHandler = MetaObjectUtils.getTargetMetaObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (mappedStatement.getSqlCommandType() != SqlCommandType.INSERT || mappedStatement.getKeyGenerator() != null && !(mappedStatement.getKeyGenerator() instanceof NoKeyGenerator)) {
            return invocation.proceed();
        }

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        Object paramObj = boundSql.getParameterObject();
        String prefix = null;
        if (paramObj instanceof Map) {
            Map paramMap = (Map)paramObj;
            Map.Entry entry = (Map.Entry)paramMap.entrySet().iterator().next();
            paramObj = entry.getValue();
            prefix = entry.getKey().toString();
        }
        Class<?> clazz = MetaUtils.getOriginalClass(paramObj.getClass());
        EntityMeta entityMeta = MetaUtils.getEntityInfo(clazz);
        EntityMeta.FieldMeta idFieldMeta = entityMeta.getIdFieldMeta();
        if (idFieldMeta == null) {
            return invocation.proceed();
        }
        Field idField = idFieldMeta.getField();
        Object id = MetaUtils.getValue(paramObj, idFieldMeta);
        if (id != null) {
            return invocation.proceed();
        }

        if (!dbDialect.useSequence()) {
            if (prefix != null) {
                ReflectUtils.setValue(mappedStatement, "keyProperties", new String[]{prefix + "." + idField.getName()});
            }
            KeyGenerator keyGenerator = Jdbc3KeyGenerator.INSTANCE;
            ReflectUtils.setValue(mappedStatement, "keyGenerator", keyGenerator);
            return invocation.proceed();
        }

        Sequence seqAno = idField.getAnnotation(Sequence.class);
        if (seqAno == null) {
            throw new RuntimeException(clazz.getName() + "未配置sequence名称");
        }

        Connection connection = (Connection) invocation.getArgs()[0];

        id = genId(connection, seqAno.name(), idField);
        MetaUtils.setValue(paramObj, idFieldMeta, id);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Object genId(Connection connection, String sequenceName, Field idField) throws Exception {
        String sql = dbDialect.sequenceSql(sequenceName);
        Object id = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Class<?> idClass = idField.getType();
                if (idClass.isAssignableFrom(Integer.class)) {
                    id = rs.getInt(1);
                } else if (idClass.isAssignableFrom(Long.class)) {
                    id = rs.getLong(1);
                } else {
                    id = rs.getString(1);
                }
            }

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.warn("Ignore this exception", e);
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Ignore this exception", e);
                }
            }

        }

        return id;
    }
}
