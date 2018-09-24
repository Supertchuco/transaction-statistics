package com.statics.transactionstatistics.junit;


import com.statics.transactionstatistics.entities.Transaction;
import com.statics.transactionstatistics.exception.IncompleteJsonStringException;
import com.statics.transactionstatistics.exception.TransactionDateInFutureException;
import com.statics.transactionstatistics.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    private SimpleDateFormat formatter;

    @Before
    public void setUp() {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void convertStringToDateObjectTest() {

        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertEquals("2018-07-17T09:59:51.312Z", outputFormat.format(transactionService.convertStringToDateObject("2018-07-17T09:59:51.312Z")));
    }

    @Test
    public void isTransactionIsOlderThan60SecondsTest() throws InterruptedException {
        Date date = new Date();
        TimeUnit.SECONDS.sleep(25);
        assertEquals(false, transactionService.isTransactionIsOlderThan60Seconds(new Transaction(15.50, date)));
    }

    @Test(expected = TransactionDateInFutureException.class)
    public void validateIfTransactionDateIsInFutureTestWhenDateIsInFuture() {
        transactionService.validateIfTransactionDateIsInFuture(new Transaction(15.50, "2035-07-17T09:59:51.312Z"));
    }

    @Test
    public void validateIfTransactionDateIsInFutureTestWhenDateIsInPast() {
        transactionService.validateIfTransactionDateIsInFuture(new Transaction(15.50, "2018-07-17T09:59:51.312Z"));
    }

    @Test
    public void validateTransactionFieldsTest() {
        transactionService.validateIfTransactionDateIsInFuture(new Transaction(15.50, "2018-07-17T09:59:51.312Z"));
    }

    @Test
    public void validateJsonStringTest() {
        transactionService.validateJsonString("{\"amount\":\"262.01\", \"timestamp\":\"4/23/2018 11:32 PM\"}");
    }

    @Test(expected = IncompleteJsonStringException.class)
    public void validateJsonStringTestWhenAmountValueIsBlank() {
        transactionService.validateJsonString("{\"amount\":\"\", \"timestamp\":\"4/23/2018 11:32 PM\"}");
    }

    @Test(expected = IncompleteJsonStringException.class)
    public void validateJsonStringTestWhenOnlyHaveAmountValue() {
        transactionService.validateJsonString("{\"amount\":\"262.01\"}");
    }

    @Test(expected = IncompleteJsonStringException.class)
    public void validateJsonStringTestWhenOnlyHaveTimestampValue() {
        transactionService.validateJsonString("{\"timestamp\":\"4/23/2018 11:32 PM\"}");
    }

    @Test(expected = IncompleteJsonStringException.class)
    public void validateJsonStringTestWhenTimeStampValueIsBlank() {
        transactionService.validateJsonString("{\"amount\":\"262.01\", \"timestamp\":\"\"}");
    }
}
