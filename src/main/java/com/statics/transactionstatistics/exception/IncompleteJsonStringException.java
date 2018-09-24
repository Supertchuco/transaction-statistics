package com.statics.transactionstatistics.exception;

public class IncompleteJsonStringException extends RuntimeException {
    public IncompleteJsonStringException(String s) {
        super(s);
    }
}