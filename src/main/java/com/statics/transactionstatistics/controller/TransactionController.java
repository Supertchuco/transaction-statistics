package com.statics.transactionstatistics.controller;

import com.statics.transactionstatistics.respository.TransactionRepository;
import com.statics.transactionstatistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;


    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Object> saveTransaction(final @RequestBody(required = false) String request) {
        HttpStatus httpStatus = transactionService.saveTransaction(transactionService.parseJsonStringToTransactionObject(request));
        return new ResponseEntity(httpStatus);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Object> deleteAllTransactions() {
        transactionService.deleteAllTransactions();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
