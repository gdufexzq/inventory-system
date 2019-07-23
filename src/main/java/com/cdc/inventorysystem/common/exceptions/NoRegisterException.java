package com.cdc.inventorysystem.common.exceptions;

public class NoRegisterException extends RuntimeException {


    private static final long serialVersionUID = 2744734185867230785L;

    public NoRegisterException() {
        super();
    }

    public NoRegisterException(String message) {
        super(message);
    }

    public NoRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRegisterException(Throwable cause) {
        super(cause);
    }

    protected NoRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
