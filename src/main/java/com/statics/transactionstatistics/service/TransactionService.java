package com.statics.transactionstatistics.service;

import com.google.gson.GsonBuilder;
import com.statics.transactionstatistics.entities.Transaction;
import com.statics.transactionstatistics.exception.*;
import com.statics.transactionstatistics.respository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Seconds;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Collection<Transaction> getTransactionsInLast60Seconds() {
        DateTime endDate = new DateTime(new Date());
        DateTime initDate = endDate.minusMinutes(1);
        return transactionRepository.getTransactionsInIntervals(initDate.toDate(), endDate.toDate());
    }

    public static Date convertStringToDateObject(final String timeStamp) {
        log.info("Convert string timestamp to date object");
        if (StringUtils.isBlank(timeStamp)) {
            throw new InvalidRequestException("Error, invalid request");
        }
        try {
            return new DateTime(timeStamp).toDateTime(DateTimeZone.UTC).toDate();
        } catch (Exception e) {
            log.error("Error to convert string timestamp to date object", e);
            throw new ConvertStringTimestampToDateObjectException("Error to convert string timestamp to date object");
        }
    }

    public void deleteAllTransactions() {
        log.info("Delete all transactions on virtual database");
        try {
            transactionRepository.deleteAll();
        } catch (Exception e) {
            log.error("Error to delete all Transactions on virtual database", e);
            throw new DeleteAllTransactionsException("Error to delete all Transactions on virtual database");
        }
    }

    public HttpStatus saveTransaction(final Transaction transaction) {
        log.info("Save transaction on virtual database");
        try {
            validateTransactionFields(transaction);
            validateIfTransactionDateIsInFuture(transaction);
            transactionRepository.save(transaction);
            return (isTransactionIsOlderThan60Seconds(transaction)) ? HttpStatus.NO_CONTENT : HttpStatus.CREATED;
        } catch (TransactionDateInFutureException e) {
            throw e;
        } catch (InvalidRequestException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error to save Transaction on virtual database", e);
            throw new SaveTransactionException("Error to save Transaction on virtual database");
        }
    }

    public boolean isTransactionIsOlderThan60Seconds(final Transaction transaction) {
        int seconds = Seconds.secondsBetween(new DateTime(transaction.getTimestamp(), DateTimeZone.UTC), new DateTime(new Date(), DateTimeZone.UTC)).getSeconds();
        log.info("seconds {}", seconds);
        if (seconds >= 60) {
            log.info("Transaction is older than 60 seconds");
            return true;
        } else {
            log.info("Transaction is not older than 60 seconds");
            return false;
        }
    }

    public void validateIfTransactionDateIsInFuture(final Transaction transaction) {
        Date currentDate = new Date();
        if (transaction.getTimestamp().after(currentDate)) {
            log.error("Transaction date is in the future");
            throw new TransactionDateInFutureException("Transaction date is in the future");
        }
    }

    public void validateTransactionFields(final Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getTimestamp() == null) {
            log.error("Error, invalid request");
            throw new InvalidRequestException("Error, invalid request");
        }
    }

    public Transaction parseJsonStringToTransactionObject(String jsonString) {
        validateJsonString(jsonString);
        try {
            return new GsonBuilder().create().fromJson(jsonString, Transaction.class);
        } catch (Exception e) {
            log.error("Error to parse json string to java object");
            throw new ParseJsonStringException("Error to parse json string to java object");
        }
    }

    public void validateJsonString(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            log.error("Json received is blank");
            throw new IncompleteJsonStringException("Json received is blank");
        }

        JSONObject jsonObj = new JSONObject(jsonString);
        if (jsonObj.length() != 2) {
            log.error("Json received is incomplete");
            throw new IncompleteJsonStringException("Json received is incomplete");
        }

        String amountValue = jsonObj.getString("amount");
        if (StringUtils.isBlank(amountValue)) {
            log.error("amount is blank");
            throw new IncompleteJsonStringException("amount is blank");
        }

        if (!NumberUtils.isNumber(amountValue)) {
            throw new IncompleteJsonStringException("Amount is not a number");
        }

        if (StringUtils.isBlank(jsonObj.getString("timestamp"))) {
            log.error("timestamp is blank");
            throw new IncompleteJsonStringException("timestamp is blank");
        }

        if (StringUtils.indexOf(jsonString, "amount") > StringUtils.indexOf(jsonString, "timestamp")) {
            throw new IncompleteJsonStringException("Payload on Json received is inverted");
        }
    }
}
