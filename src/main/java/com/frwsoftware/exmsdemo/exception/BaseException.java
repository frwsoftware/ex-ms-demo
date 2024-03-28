package com.frwsoftware.exmsdemo.exception;

public class BaseException extends Throwable {
    private String message;
    private Throwable originalException;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }
    public BaseException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.originalException = cause;
    }
    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.originalException = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Throwable getOriginalException() {
        return originalException;
    }

}
