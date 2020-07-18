package org.horx.wdf.common.mybatis.config;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.mybatis.config.wrapper.PagingResultWrapper;

/**
 * 扩展对象封装Factory。
 * @since 1.0
 */
public class ExtendedObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof PagingResult;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if (object instanceof PagingResult) {
            return new PagingResultWrapper(metaObject, (PagingResult)object);
        }
        return null;
    }
}
