package org.horx.wdf.sys.mapper;

import org.horx.wdf.sys.domain.User;

import javax.persistence.Column;

public class SysUserExt extends User {
    @Column
    private String ext1;

    @Column
    private String ext2;

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
}
