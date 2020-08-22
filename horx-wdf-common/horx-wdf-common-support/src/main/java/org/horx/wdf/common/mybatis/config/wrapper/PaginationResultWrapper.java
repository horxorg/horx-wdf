package org.horx.wdf.common.mybatis.config.wrapper;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.config.PaginationList;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果封装累。
 * @since 1.0
 */
public class PaginationResultWrapper implements ObjectWrapper {
    private final PaginationResult paginationResult;

    public PaginationResultWrapper(MetaObject metaObject, PaginationResult paginationResult) {
        this.paginationResult = paginationResult;
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
        List list = paginationResult.getData();
        if (list == null) {
            list = new ArrayList();
            paginationResult.setData(list);
        }
        list.add(element);
    }

    @Override
    public <E> void addAll(List<E> element) {
        List list = paginationResult.getData();
        if (list == null) {
            paginationResult.setData(element);
        } else {
            list.addAll(element);
        }

        if (element instanceof PaginationList) {
            PaginationList plist = (PaginationList) element;
            PaginationRowBounds paginationRowBounds = plist.getPaginationRowBounds();
            if (paginationRowBounds != null) {
                paginationResult.setTotal(paginationRowBounds.getTotal());
            }

        }
    }
}
