package com.statics.transactionstatistics.respository;


import com.statics.transactionstatistics.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class DatabaseLoader implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    @Autowired
    public DatabaseLoader(TransactionRepository transactionRepo) {
        this.transactionRepository = transactionRepo;
    }

    @Override
    public void run(String... strings) {
        this.transactionRepository.save(new Transaction(15.50, "2018-07-17T09:59:51.312Z"));
        this.transactionRepository.save(new Transaction(19.50, new Date()));
        // this.transactionRepository.save(new Transaction(10.50, new Date()));
        //this.transactionRepository.save(new Transaction(18.25, new Date()));
    }

}
