package org.horx.wdf.common.extension.level.support;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.extension.level.LevelCodeGenerator;

import java.util.UUID;

/**
 * 树形层级编码生成器。
 * @since 1.0
 */
public class IdLevelCodeGenerator implements LevelCodeGenerator {
    private final static char[] CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K','L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k','l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '{', '}'};

    @Override
    public boolean generateAfterInsert(String bizCode, Long id) {
        return (id == null);
    }

    @Override
    public String getTempLevelCode(String bizCode, Long parentId, String parentLevelCode) {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getLevelCode(String bizCode, Long parentId, String parentLevelCode, Long id) {
        return (StringUtils.isEmpty(parentLevelCode)) ? toCode(id) : parentLevelCode + "-" + toCode(id);
    }

    /**
     * 根据编码得到数字。
     * @param code
     * @return
     */
    public long toNum(String code) {
        if (StringUtils.isEmpty(code)) {
            return 0L;
        }
        long num = 0;
        for (int i = 0, len = code.length(); i < len; i++) {
            char chr = code.charAt(i);
            int p = -1;
            for (int j = 0; j < 64; j++) {
                if (CHARS[j] == chr) {
                    p = j;
                    break;
                }
            }
            num += (p << (6 * (len - i - 1)));
        }
        return num;
    }

    /**
     * 根据数字得到编码。
     * @param num
     * @return
     * @throws Exception
     */
    public String toCode(long num) {
        long tmp = num;
        StringBuilder str = new StringBuilder();
        while (tmp > 0) {
            int left = (int)(tmp % 64);
            str.insert(0, CHARS[left]);
            tmp = tmp / 64;
        }

        return str.toString();
    }

    public static void main(String[ ]args) {
        IdLevelCodeGenerator id = new IdLevelCodeGenerator();
        System.out.println(id.toCode(18L));
    }
}
