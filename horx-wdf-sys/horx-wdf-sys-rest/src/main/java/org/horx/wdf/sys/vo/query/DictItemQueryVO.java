package org.horx.wdf.sys.vo.query;

import org.horx.wdf.common.filed.value.annotation.PathParameter;
import org.horx.wdf.common.filed.value.annotation.RequestParameter;

import java.io.Serializable;

/**
 * 字典项查询条件VO。
 * @since 1.0
 */
public class DictItemQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PathParameter
    private Long dictId;

    @RequestParameter()
    private String code;

    @RequestParameter()
    private String name;

    @RequestParameter()
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
