package com.mapfre.tron.evn.exceptions;

public class ThirdPartyReaderException extends Exception {

    public ThirdPartyReaderException(String message) {
        super(message);
    }

    public ThirdPartyReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdPartyReaderException(Throwable cause) {
        super(cause);
    }
}
