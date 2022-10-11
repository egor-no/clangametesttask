package com.clangame.demo.services;

import com.clangame.demo.data.entities.Clan;

import java.util.List;

// Есть сервис, посвященный кланам.
// Да это выглядит как 'репозиторий'.
// Но это сервис, просто все остальные методы не нужны для примера
public interface ClanService {
    Clan get(long clanId);
    long save(Clan clan);
    void update(Clan clan);
    void delete(long clanId);
    List<Clan> getAll(); //я всё же добавил один метод, хоть он и не нужен для примера
}