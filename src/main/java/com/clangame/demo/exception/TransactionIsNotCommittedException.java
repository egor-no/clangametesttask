package com.clangame.demo.exception;

import com.clangame.demo.data.entities.Transaction;

public class TransactionIsNotCommittedException extends Exception {

    public TransactionIsNotCommittedException() {
        super("Transaction wasn't successful. Please try again.");
    }
}
