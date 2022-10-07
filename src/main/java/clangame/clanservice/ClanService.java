package clangame.clanservice;

import clangame.clan.Clan;

// Есть сервис, посвященный кланам.
// Да это выглядит как 'репозиторий'.
// Но это сервис, просто все остальные методы не нужны для примера
public interface ClanService {
    Clan get(long clanId);
    void save(Clan clan);
}