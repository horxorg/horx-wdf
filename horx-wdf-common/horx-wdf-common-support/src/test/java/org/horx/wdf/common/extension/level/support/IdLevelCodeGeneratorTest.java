package org.horx.wdf.common.extension.level.support;

import org.junit.Test;

public class IdLevelCodeGeneratorTest {

    @Test
    public void toCodeTest() {
        IdLevelCodeGenerator idLevelCodeGenerator = new IdLevelCodeGenerator();
        System.out.println(idLevelCodeGenerator.toCode(32L));
    }
}
