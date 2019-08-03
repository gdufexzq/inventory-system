package com.cdc.inventorysystem.common.exceptions;

/**
 * @author xzquan
 * @version V1.0
 * @Description: ${TODO}
 * @date 2019/6/17 10:58
 */
public class NoAuthException extends RuntimeException {

    private static final long serialVersionUID = -883116303894151865L;

    public NoAuthException() {
        super();
    }

    public NoAuthException(String message) {
        super(message);
    }

    public NoAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAuthException(Throwable cause) {
        super(cause);
    }

    protected NoAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
