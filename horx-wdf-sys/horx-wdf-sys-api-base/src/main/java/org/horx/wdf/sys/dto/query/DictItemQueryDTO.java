package org.horx.wdf.sys.dto.query;

import java.io.Serializable;

/**
 * 字典项查询条件DTO。
 * @since 1.0
 */
public class DictItemQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long dictId;

    private String code;

    private String name;

    private Boolean enabled;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
