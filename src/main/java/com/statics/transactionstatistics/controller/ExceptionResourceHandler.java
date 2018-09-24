package com.statics.transactionstatistics.controller;

import com.statics.transactionstatistics.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionResourceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions() {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaveTransactionException.class)
    public final ResponseEntity<ExceptionResponse> handleSaveTransactionException(WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Error during save transaction information, please contact the support for more information",
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DeleteAllTransactionsException.class)
    public final ResponseEntity<ExceptionResponse> handleDeleteAllTransactionsException(WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Error during delete all transactions information on virtual database, please contact the support for more information",
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity handleInvalidRequestException() {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TransactionDateInFutureException.class)
    public final ResponseEntity handleTransactionDateInFutureException() {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IncompleteJsonStringException.class)
    public final ResponseEntity handleIncompleteJsonStringException() {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ParseJsonStringException.class)
    public final ResponseEntity handleParseJsonStringException() {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorToGenerateStatisticsReportException.class)
    public final ResponseEntity<ExceptionResponse> handleErrorToGenerateStatisticsReportException(WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Error to generate Statistics Report, please contact the support for more information",
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
