package com.clangame.demo.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clan {

    private long id;     // id клана
    private String name; // имя клана
    private int gold;    // текущее количество золота в казне клана

}