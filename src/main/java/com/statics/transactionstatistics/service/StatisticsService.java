package com.statics.transactionstatistics.service;

import com.statics.transactionstatistics.entities.Transaction;
import com.statics.transactionstatistics.exception.ErrorToGenerateStatisticsReportException;
import com.statics.transactionstatistics.vo.StatisticResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;

@Service
@Slf4j
public class StatisticsService {

    @Autowired
    TransactionService transactionService;

    public StatisticResponseVO buildStatisticsReportBy60Seconds() {
        Collection<Transaction> transactions = transactionService.getTransactionsInLast60Seconds();
        try {
            log.info("Generate Statistics Report by Last 60 Seconds");
            if (CollectionUtils.isEmpty(transactions)) {
                log.info("Transactions not found in last 60 seconds");
                return new StatisticResponseVO(transactions.size());
            } else {
                log.info("Found {} Transactions found in last 60 seconds", transactions.size());
                return new StatisticResponseVO(getTotalTransactionsValue(transactions), getAverageTransactionValue(transactions),
                        getHigherTransactionValue(transactions), getLowerTransactionValue(transactions), transactions.size());
            }
        } catch (Exception e) {
            log.error("Error to generate Statistics Report", e);
            throw new ErrorToGenerateStatisticsReportException("Error to generate Statistics Report");
        }
    }

    public double getHigherTransactionValue(final Collection<Transaction> transactions) {
        log.info("Get Transaction with Higher Value");
        return transactions.stream().max(Comparator.comparing(Transaction::getAmount)).get().getAmount();
    }

    public double getLowerTransactionValue(final Collection<Transaction> transactions) {
        log.info("Get Transaction with Lower Value");
        return transactions.stream().min(Comparator.comparing(Transaction::getAmount)).get().getAmount();
    }

    public double getTotalTransactionsValue(final Collection<Transaction> transactions) {
        log.info("Total Transactions Value");
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public double getAverageTransactionValue(final Collection<Transaction> transactions) {
        log.info("Average of Transaction Values");
        return transactions.stream().mapToDouble(Transaction::getAmount).average().getAsDouble();
    }
}
