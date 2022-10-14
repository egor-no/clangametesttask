package com.clangame.demo.data.entities;

import com.clangame.demo.data.tools.GoldSource;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Transaction {

    private long id;
    private int delta; //на сколько золота меняется "счёт"
    private GoldSource source;
    private Timestamp timestamp;
    private boolean successful;
    private long clanId;
}