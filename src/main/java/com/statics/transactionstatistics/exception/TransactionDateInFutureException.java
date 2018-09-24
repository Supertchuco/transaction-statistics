package com.statics.transactionstatistics.exception;

public class TransactionDateInFutureException extends RuntimeException {
    public TransactionDateInFutureException(String s) {
        super(s);
    }
}