package com.statics.transactionstatistics.junit;

import com.statics.transactionstatistics.entities.Transaction;
import com.statics.transactionstatistics.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class StaticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    private Collection<Transaction> transactions;

    @Before
    public void setUp() {
        transactions = buildTransactionCollection();
    }

    private Collection<Transaction> buildTransactionCollection() {
        Collection<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(15.50, "2018-07-17T09:59:51.312Z"));
        transactions.add(new Transaction(10.00, "2018-07-17T09:58:51.312Z"));
        transactions.add(new Transaction(5.00, "2018-06-17T09:59:51.312Z"));
        return transactions;
    }

    @Test
    public void getHigherTransactionValueTest() {
        assertEquals(15.50, statisticsService.getHigherTransactionValue(transactions), 0.001);
    }

    @Test
    public void getLowerTransactionValueTest() {
        assertEquals(5.00, statisticsService.getLowerTransactionValue(transactions), 0.001);
    }

    @Test
    public void getTotalTransactionsValueTest() {
        assertEquals(30.50, statisticsService.getTotalTransactionsValue(transactions), 0.001);
    }

    @Test
    public void getAverageTransactionValueTest() {
        assertEquals(10.16, statisticsService.getAverageTransactionValue(transactions), 0.007);
    }

}
