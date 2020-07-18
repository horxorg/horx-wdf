package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;

import java.io.Serializable;

/**
 * 字典批量操作DTO。
 * @since 1.0
 */
public class BatchWithDictAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long[] ids;

    private DictDataAuthDTO dictDataAuth;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public DictDataAuthDTO getDictDataAuth() {
        return dictDataAuth;
    }

    public void setDictDataAuth(DictDataAuthDTO dictDataAuth) {
        this.dictDataAuth = dictDataAuth;
    }
}
