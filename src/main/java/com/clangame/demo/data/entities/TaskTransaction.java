package com.clangame.demo.data.entities;

import com.clangame.demo.data.tools.GoldSource;
import lombok.Data;

@Data
public class TaskTransaction {

    private Transaction transaction;
    private long taskId;

    public long getTransactionId() {
        return transaction.getId();
    }

}
