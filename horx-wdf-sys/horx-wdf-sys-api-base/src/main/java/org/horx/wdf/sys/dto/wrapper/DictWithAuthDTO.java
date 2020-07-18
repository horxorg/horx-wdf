package org.horx.wdf.sys.dto.wrapper;

import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;

import java.io.Serializable;

/**
 * 字典操作DTO。
 * @since 1.0
 */
public class DictWithAuthDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private DictDTO dict;

    private DictDataAuthDTO dictDataAuth;

    public DictDTO getDict() {
        return dict;
    }

    public void setDict(DictDTO dict) {
        this.dict = dict;
    }

    public DictDataAuthDTO getDictDataAuth() {
        return dictDataAuth;
    }

    public void setDictDataAuth(DictDataAuthDTO dictDataAuth) {
        this.dictDataAuth = dictDataAuth;
    }
}
