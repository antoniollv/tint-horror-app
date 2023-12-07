package com.mapfre.tron.evn.exceptions;

public class ThirdPartyWriterException extends Exception {

    public ThirdPartyWriterException(String message) {
        super(message);
    }

    public ThirdPartyWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdPartyWriterException(Throwable cause) {
        super(cause);
    }
}
