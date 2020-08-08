package com.ntabnk.sales.application.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Exception err) {
        super(message, err);
    }

}
