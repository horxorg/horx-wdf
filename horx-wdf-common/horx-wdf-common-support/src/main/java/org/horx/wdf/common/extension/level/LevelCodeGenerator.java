package org.horx.wdf.common.extension.level;

/**
 * 树形层级编码生成器接口。
 * @since 1.0
 */
public interface LevelCodeGenerator {

    boolean generateAfterInsert(String bizCode, Long id);

    String getTempLevelCode(String bizCode, Long parentId, String parentLevelCode);

    String getLevelCode(String bizCode, Long parentId, String parentLevelCode, Long id);
}
