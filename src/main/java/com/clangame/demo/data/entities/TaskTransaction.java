package com.clangame.demo.data.entities;

import com.clangame.demo.data.tools.GoldSource;

public class TaskTransaction {

    private Transaction goldTransaction;
    private long taskId;

    public TaskTransaction() {
        goldTransaction = new Transaction();
        goldTransaction.setSource(GoldSource.USER_ADD);
    }
}
