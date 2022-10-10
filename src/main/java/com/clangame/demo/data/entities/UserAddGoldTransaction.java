package com.clangame.demo.data.entities;

import com.clangame.demo.data.tools.GoldSource;
import lombok.Data;

@Data
public class UserAddGoldTransaction {

    private Transaction transaction;
    private long userId;

}
