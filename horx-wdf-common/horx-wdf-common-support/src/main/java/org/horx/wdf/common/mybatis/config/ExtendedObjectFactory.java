package org.horx.wdf.common.mybatis.config;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.horx.wdf.common.entity.PagingResult;

/**
 * 扩展对象Factory。
 * @since 1.0
 */
public class ExtendedObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return super.isCollection(type) || type.isAssignableFrom(PagingResult.class);
    }
}
