package org.horx.wdf.common.exception;

import org.horx.wdf.common.entity.Result;

/**
 * Result对象表示的异常。
 * @since 1.0
 */
public class ResultException extends RuntimeException {

    private Result result;

    public ResultException() {
        super();
    }

    public ResultException(Result result) {
        super();
        this.result = result;
    }

    public ResultException(String message, Result result) {
        super(message);
        this.result = result;
    }

    public ResultException(String message, Result result, Throwable throwable) {
        super(message, throwable);
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
