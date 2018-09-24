package com.statics.transactionstatistics.respository;


import com.statics.transactionstatistics.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select transaction from Transaction transaction where transaction.timestamp between :dateInit and :dateFinal")
    Collection<Transaction> getTransactionsInIntervals(
            final @Param("dateInit") Date dateInit, final @Param("dateFinal") Date dateFinal);
}
