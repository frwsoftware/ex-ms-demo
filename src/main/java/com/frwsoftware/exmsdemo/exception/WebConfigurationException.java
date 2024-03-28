package com.frwsoftware.exmsdemo.exception;

public class WebConfigurationException  extends BaseException {
    public WebConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
    public WebConfigurationException(Throwable cause) {
        super(cause);
    }
    public WebConfigurationException(String message) {
        super(message);
    }

}
