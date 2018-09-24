package com.statics.transactionstatistics.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.DecimalFormat;

@Data
public class StatisticResponseVO {

    @JsonProperty("sum")
    private String totalTransactionsValue;

    @JsonProperty("avg")
    private String averageAllTransactionValues;

    @JsonProperty("max")
    private String higherTransactionValue;

    @JsonProperty("min")
    private String lowestTransactionValue;

    @JsonProperty("count")
    private long totalTransactionsOccurred;

    @JsonIgnore
    DecimalFormat formatter = new DecimalFormat("0.00");

    public StatisticResponseVO(final double totalTransactionsValue, final double averageAllTransactionValues, final double higherTransactionValue,
                               final double lowestTransactionValue, final long totalTransactionsOccurred) {
        this.totalTransactionsValue = formatter.format(totalTransactionsValue);
        this.averageAllTransactionValues = formatter.format(averageAllTransactionValues);
        this.higherTransactionValue = formatter.format(higherTransactionValue);
        this.lowestTransactionValue = formatter.format(lowestTransactionValue);
        this.totalTransactionsOccurred = totalTransactionsOccurred;
    }

    public StatisticResponseVO(final long totalTransactionsOccurred) {
        final String EMPTY_VALUE = "0.00";
        this.totalTransactionsValue = EMPTY_VALUE;
        this.averageAllTransactionValues = EMPTY_VALUE;
        this.higherTransactionValue = EMPTY_VALUE;
        this.lowestTransactionValue = EMPTY_VALUE;
        this.totalTransactionsOccurred = totalTransactionsOccurred;
    }

}
