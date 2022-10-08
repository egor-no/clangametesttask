package com.clangame.demo.data.entities;

import lombok.Data;

@Data
public class Clan {

    private long id;     // id клана
    private String name; // имя клана
    private int gold;    // текущее количество золота в казне клана

}