package org.horx.wdf.common.mybatis.interceptor;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * 元数据工具类。
 * @since 1.0
 */
final class MetaObjectUtils {
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    private MetaObjectUtils() {}

    public static MetaObject getMetaObject(Object obj) {
        MetaObject metaObject = MetaObject.forObject(obj, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        return metaObject;
    }

    public static MetaObject getTargetMetaObject(Object obj) {
        MetaObject metaObject = getMetaObject(obj);

        while (metaObject.hasGetter("h")) {
            metaObject = getTargetObject(metaObject);
        }

        return metaObject;
    }

    private static MetaObject getTargetObject(MetaObject metaObject) {
        Object object = metaObject.getValue("h");
        metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);

        if (metaObject.hasGetter("target")) {
            object = metaObject.getValue("target");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        }

        return metaObject;
    }
}
