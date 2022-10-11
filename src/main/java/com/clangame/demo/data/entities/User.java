package com.clangame.demo.data.entities;

import lombok.Data;

@Data
public class User {

    private long id;
    private long clanId;
    private String name;
    private String surname;

}
