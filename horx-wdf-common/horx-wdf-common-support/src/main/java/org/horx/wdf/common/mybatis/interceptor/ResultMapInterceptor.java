package org.horx.wdf.common.mybatis.interceptor;

import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.common.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ResultMap拦截器。
 * @since 1.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class ResultMapInterceptor implements Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(ResultMapInterceptor.class);

    private EntityExtension entityExtension;

    public void setEntityExtension(EntityExtension entityExtension) {
        this.entityExtension = entityExtension;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaStatementHandler = MetaObjectUtils.getTargetMetaObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (mappedStatement.getSqlCommandType() != SqlCommandType.SELECT) {
            return invocation.proceed();
        }

        List<ResultMap> rms = mappedStatement.getResultMaps();
        ResultMap rm = null;
        if (rms != null && rms.size() > 0) {
            rm = rms.get(0);
        }

        if (rm != null && (rm.getResultMappings() == null || rm.getResultMappings().size() == 0)) {
            rm = genResultMap(mappedStatement.getConfiguration(), mappedStatement, rm.getType());
            if (rm != null) {
                rms = new ArrayList<ResultMap>(1);
                rms.add(rm);

                ReflectUtils.setValue(mappedStatement, "resultMaps", rms);
            }
        }

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

    private ResultMap genResultMap(Configuration configuration,  MappedStatement mappedStatement, Class<?> clazz) {
        if (clazz.isAssignableFrom(PaginationResult.class) && mappedStatement.getSqlSource() != null && mappedStatement.getSqlSource() instanceof ProviderSqlSource) {
            MetaObject metaObject = MetaObjectUtils.getTargetMetaObject(mappedStatement.getSqlSource());
            Class<?> cls = (Class<?>)metaObject.getValue("providerContext.mapperType");
            clazz = ReflectUtils.getParameterizedClassFromInterface(cls, 0);
        }
        Class<?> subClass = (entityExtension == null) ? clazz : entityExtension.getExtensionClass(clazz);
        EntityMeta entityMeta = MetaUtils.getEntityInfo(subClass);
        if (entityMeta == null) {
            return null;
        }

        EntityMeta.FieldMeta[] fieldMetas = entityMeta.getFieldMetas();

        List<ResultMapping> rmList = new ArrayList<ResultMapping>(fieldMetas.length);

        for (EntityMeta.FieldMeta fieldMeta : fieldMetas) {
            if (fieldMeta.getColumnName() == null) {
                continue;
            }
            Field field = fieldMeta.getField();
            ResultMapping.Builder rb = new ResultMapping.Builder(configuration, field.getName(), fieldMeta.getColumnName(), field.getType());
            rb.javaType(field.getType());
            if (field.getType().isAssignableFrom(Boolean.class)) {
                rb = rb.jdbcType(JdbcType.INTEGER);
            }
            ResultMapping rm = rb.build();

            rmList.add(rm);
        }

        ResultMap resultMap = new ResultMap.Builder(configuration, clazz.getName() + "_InterceptorGen", subClass, rmList).build();

        return resultMap;
    }
}
