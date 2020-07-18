package org.horx.wdf.common.extension.result;

import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.entity.Result;

/**
 * 结果转换。
 * @since 1.0
 */
public interface ResultConverter {

    /**
     * 转换Result。
     * @param result
     * @return
     */
    Object convertResult(Result result);

    /**
     * 转换分页Result。
     * @param pageResult
     * @return
     */
    Object convertPagingResult(PagingResult pageResult);
}
