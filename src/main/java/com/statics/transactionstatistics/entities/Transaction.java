package com.statics.transactionstatistics.entities;

import com.statics.transactionstatistics.service.TransactionService;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "Transaction")
@Table(name = "Transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private int transactionId;

    @Column
    private Double amount;

    @Column
    private Date timestamp;

    public Transaction(double amount, String timeStamp) {
        this.amount = amount;
        this.timestamp = TransactionService.convertStringToDateObject(timeStamp);
    }

    public Transaction(double amount, Date timeStamp) {
        this.amount = amount;
        this.timestamp = timeStamp;
    }

    public Transaction() {
    }

}
