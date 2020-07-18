package org.horx.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Json工具类。
 * @since 1.0
 */
public final class JsonUtils {
    private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectmapper;

    private JsonUtils() {}

    static {

        objectmapper = new ObjectMapper();
        objectmapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectmapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectmapper.setSerializationInclusion(Include.NON_NULL);
        objectmapper.setTimeZone(TimeZone.getDefault());
        //objectmapper.setConfig(SerializationConfig )
        //objectmapper.setSerializerFactory(factory);
    }

    /**
     * 对象序列化为json字符串。
     * @param object 待转换的对象。
     * @return json字符串。
     */
    public static String toJson(Object object) {
        String json = null;
        try {
            Writer strWriter = new StringWriter();
            objectmapper.writeValue(strWriter, object);
            json = strWriter.toString();
        } catch (Exception e) {
            logger.error("无法把对象序列化为json", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return json;
    }

    /**
     * 对象序列化为json字符串。
     * @param object 待转换的对象。
     * @param dateFormat 日期格式。
     * @return json字符串。
     */
    public static String toJson(Object object, DateFormat dateFormat) {
        String json = null;
        try {
            ObjectMapper newObjectMapper = (dateFormat == null) ? objectmapper : objectmapper.copy();
            newObjectMapper.setDateFormat(dateFormat);
            Writer strWriter = new StringWriter();
            newObjectMapper.writeValue(strWriter, object);
            json = strWriter.toString();
        } catch (Exception e) {
            logger.error("无法把对象序列化为json", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return json;
    }

    /**
     * json字符串反序列化为对象。
     * @param json json字符串
     * @param clazz 对象类型的class。
     * @param <T> 对象类型。
     * @return 对象。
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = objectmapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("无法把json序列化为对象:" + json, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return obj;
    }

    /**
     * 由输入流反序列化为对象。
     * @param inputStream 输入流。
     * @param clazz 对象类型的class。
     * @param <T> 对象类型。
     * @return 对象。
     */
    public static <T> T fromJson(InputStream inputStream, Class<T> clazz) {
        T obj = null;
        try {
            obj = objectmapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            logger.error("无法把json序列化为对象", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return obj;
    }

    /**
     * 由输入流反序列化为对象。
     * @param inputStream
     * @param javaType
     * @return
     */
    public static Object fromJson(InputStream inputStream, JavaType javaType) {
        Object obj = null;
        try {
            obj = objectmapper.readValue(inputStream, javaType);
        } catch (Exception e) {
            logger.error("无法把json序列化为对象", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return obj;
    }

    /**
     * 由输入流反序列化为对象。
     * @param inputStream
     * @param type
     * @return
     */
    public static Object fromJson(InputStream inputStream, Type type) {
        Object obj = null;
        try {
            JavaType javaType = getJavaType(type);
            obj = objectmapper.readValue(inputStream, javaType);
        } catch (Exception e) {
            logger.error("无法把json序列化为对象", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return obj;
    }

    /**
     * json字符串反序列化为对象。
     * @param json json字符串
     * @param type 类型引用。
     * @param <T> 对象类型。
     * @return 对象。
     */
    public static <T> T fromJson(String json, TypeReference<T> type) {
        T obj = null;
        try {
            obj = objectmapper.readValue(json, type);

        } catch (Exception e) {
            logger.error("无法把json序列化为对象", e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return obj;
    }

    private static JavaType getJavaType(Type type) {
        return objectmapper.getTypeFactory().constructType(type);
    }
}
