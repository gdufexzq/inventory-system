package com.cdc.inventorysystem.common.exceptions;

/**
 * @author lwq
 * @version V1.0
 * @date 2019/8/4
 */
public class AccountLockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AccountLockException() {
        super();
    }

    public AccountLockException(String message) {
        super(message);
    }

    public AccountLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountLockException(Throwable cause) {
        super(cause);
    }

    protected AccountLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

