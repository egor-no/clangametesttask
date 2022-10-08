package com.clangame.demo.data.entities;

import com.clangame.demo.data.tools.GoldSource;
import lombok.Data;

@Data
public class UserAddGoldTransaction {

    private Transaction goldTransaction;
    private long userId;

    public UserAddGoldTransaction() {
        goldTransaction = new Transaction();
        goldTransaction.setSource(GoldSource.USER_ADD);
    }
}
