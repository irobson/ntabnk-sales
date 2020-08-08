package com.ntabnk.sales.application.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Exception e) {
        super(message, e);
    }

}
