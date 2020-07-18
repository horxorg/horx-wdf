package org.horx.wdf.common.mybatis.config.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.mybatis.config.PagingList;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果封装累。
 * @since 1.0
 */
public class PagingResultWrapper implements ObjectWrapper {
    private final PagingResult pagingResult;

    public PagingResultWrapper(MetaObject metaObject, PagingResult pagingResult) {
        this.pagingResult = pagingResult;
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getGetterNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getSetterNames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getSetterType(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<?> getGetterType(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasSetter(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasGetter(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCollection() {
        return true;
    }

    @Override
    public void add(Object element) {
        List list = pagingResult.getData();
        if (list == null) {
            list = new ArrayList();
            pagingResult.setData(list);
        }
        list.add(element);
    }

    @Override
    public <E> void addAll(List<E> element) {
        List list = pagingResult.getData();
        if (list == null) {
            pagingResult.setData(element);
        } else {
            list.addAll(element);
        }

        if (element instanceof PagingList) {
            PagingList plist = (PagingList) element;
            PagingRowBounds pagingRowBounds = plist.getPagingRowBounds();
            if (pagingRowBounds != null) {
                pagingResult.setTotal(pagingRowBounds.getTotal());
            }

        }
    }
}
