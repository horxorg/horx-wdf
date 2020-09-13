package org.horx.wdf.common.filed.value;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.exception.ResultException;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 属性赋值上下文。
 * @since 1.0
 */
public class ValueContext {
    private final static Logger logger = LoggerFactory.getLogger(ValueContext.class);

    private Object entity;
    private Class<?>[] groups;
    private EntityMeta entityMeta;

    private Map<String, Object> attrMap = new HashMap<>();

    /**
     * 构造方法。
     * @param entity 对象。
     * @param entityMeta 实体元数据。
     * @param groups 赋值操作的组。
     */
    public ValueContext(Object entity, EntityMeta entityMeta, Class<?>[] groups) {
        this.entity = entity;
        this.entityMeta = entityMeta;
        this.groups = groups;
    }

    /**
     * 设置上下文属性。
     * @param key 属性key。
     * @param value 属性值。
     */
    public void setAttribute(String key, Object value) {
        attrMap.put(key, value);
    }

    /**
     * 获取上下文属性。
     * @param key 属性key。
     * @return 属性值。
     */
    public Object getAttribute(String key) {
        return attrMap.get(key);
    }

    /**
     * 类型转换。
     * @param value 原值。
     * @param field 属性。
     * @param pattern 格式。
     * @return 转换后的值。
     */
    public Object convertType(Object value, Field field, String pattern) {
        if (value == null) {
            return null;
        }

        Class<?> type = field.getType();
        if (value instanceof String && value.toString().length() == 0 && !type.isAssignableFrom(String.class)) {
            return null;
        }

        if (type.isArray()) {
            if (type.getComponentType().isAssignableFrom(Date.class)) {
                if (value.getClass().isArray() && value.getClass().getComponentType().isAssignableFrom(Date.class)) {
                    return value;
                } else {
                    int len = Array.getLength(value);
                    Date[] dateArr = new Date[len];
                    for (int i = 0; i < len; i++) {
                        dateArr[i] = convertDate(Array.get(value, i), pattern);
                    }
                    return dateArr;
                }
            }
        } else if (type.isAssignableFrom(Date.class)) {
            return convertDate(value, pattern);
        }

        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        return typeConverter.convertIfNecessary(value, type);
    }

    /**
     * 简单类型转换。
     * @param value 原值。
     * @param field 属性。
     * @return 转换后的值。
     */
    public Object convertSimpleType(Object value, Field field) {
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        return typeConverter.convertIfNecessary(value, field.getType());
    }

    /**
     * 执行表达式。
     * @param value 原值。
     * @param exprStr spring表达式。
     *                参见<code>https://docs.spring.io/spring/docs/5.0.0.M5/spring-framework-reference/html/expressions.html</code>
     * @return 转换后的值。
     */
    public Object execExpression(Object value, String exprStr) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext elContext = new StandardEvaluationContext();
        elContext.setBeanResolver(new BeanFactoryResolver(SpringContext.getApplicationContext()));
        elContext.setVariable("value", value);
        elContext.setVariable("entity", entity);
        Expression expr = parser.parseExpression(exprStr);
        return expr.getValue(elContext);
    }

    /**
     * 获取对象。
     * @return
     */
    public Object getEntity() {
        return entity;
    }

    /**
     * 获取赋值操作的组。
     * @return
     */
    public Class<?>[] getGroups() {
        return groups;
    }

    /**
     * 判断是否有某个组。
     * @param group
     * @return
     */
    public boolean hasGroup(Class<?> group) {
        if (group != null) {
            for (Class<?> item : groups) {
                if (group == item)  {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取实体元数据。
     * @return
     */
    public EntityMeta getEntityMeta() {
        return entityMeta;
    }

    private Date convertDate(Object value, String pattern) {
        if (value instanceof Date) {
            return (Date)value;
        }
        String str = String.valueOf(value).trim();
        Date date = null;
        try {
            if (StringUtils.isEmpty(pattern)) {
                date = DateUtils.parse(str);
            } else {
                date = DateUtils.parse(str, pattern);
            }
        } catch (ParseException e) {
            logger.error("解析" + str + "错误，格式：" + pattern);
            MsgTool msgTool = SpringContext.getBean(MsgTool.class);
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(), msgTool.getMsg("common.err.param") + ":" + str));
        }
        return date;
    }
}
