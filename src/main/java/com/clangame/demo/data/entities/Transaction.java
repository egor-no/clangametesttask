package com.clangame.demo.data.entities;

import com.clangame.demo.data.tools.GoldSource;
import lombok.Data;

import java.util.Date;

@Data
public class Transaction {

    private long id;
    private int delta; //на сколько золота меняется "счёт"
    private GoldSource source;
    private Date date;
    private boolean successful;
    private long clanId;
}