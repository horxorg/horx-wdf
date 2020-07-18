package org.horx.wdf.common.spring.http.converter;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.extension.result.ResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 使用fasterxml解析json。
 * @since 1.0
 */
public class JsonHttpMessageConverter extends AbstractGenericHttpMessageConverter<Object> {
    private final static Logger logger = LoggerFactory.getLogger(JsonHttpMessageConverter.class);

    protected ResultConverter resultConverter;

    public JsonHttpMessageConverter() {
        super(MediaType.APPLICATION_JSON_UTF8);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        try {
            return JsonUtils.fromJson(inputMessage.getBody(), type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void writeInternal(Object t, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        String json = null;
        try {
            Object convertedResult = t;
            if (resultConverter != null && t != null) {
                if (t instanceof PagingResult) {
                    convertedResult = resultConverter.convertPagingResult((PagingResult)t);
                } else if (t instanceof Result) {
                    convertedResult = resultConverter.convertResult((Result)t);
                }
            }

            json = JsonUtils.toJson(convertedResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (json != null) {
            outputMessage.getBody().write(json.getBytes("UTF-8"));
        }
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        try {
            return JsonUtils.fromJson(inputMessage.getBody(), clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 设置结果转换。
     * @param resultConverter
     */
    public void setResultConverter(ResultConverter resultConverter) {
        this.resultConverter = resultConverter;
    }
}
