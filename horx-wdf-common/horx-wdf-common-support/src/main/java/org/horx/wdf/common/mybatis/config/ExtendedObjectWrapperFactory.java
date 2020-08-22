package org.horx.wdf.common.mybatis.config;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.config.wrapper.PaginationResultWrapper;

/**
 * 扩展对象封装Factory。
 * @since 1.0
 */
public class ExtendedObjectWrapperFactory implements ObjectWrapperFactory {
    @Override
    public boolean hasWrapperFor(Object object) {
        return object instanceof PaginationResult;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if (object instanceof PaginationResult) {
            return new PaginationResultWrapper(metaObject, (PaginationResult)object);
        }
        return null;
    }
}
