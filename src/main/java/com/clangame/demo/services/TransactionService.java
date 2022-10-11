package com.clangame.demo.services;

import com.clangame.demo.data.dao.TransactionDAO;
import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.tools.GoldSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TransactionService {

    @Inject
    private TransactionDAO transactionDAO;

    @Inject
    private ClanService clanService;

    public Transaction get(long transactionId) {
        return transactionDAO.get(transactionId).orElse(null);
    }

    public void save(Transaction transaction) {
        transactionDAO.save(transaction);
        Clan clan = clanService.get(transaction.getClanId());
        int newBalance = clan.getGold() + transaction.getDelta();
        clan.setGold(newBalance);
        clanService.save(clan);
    }

    public void update(Transaction transaction) {
        int oldDelta = transactionDAO.get(transaction.getId()).get().getDelta();
        int newDelta = transaction.getDelta();

        transactionDAO.update(transaction);
        if (oldDelta != newDelta) {
            int change = newDelta - oldDelta;
            Clan clan = clanService.get(transaction.getClanId());
            int newBalance = clan.getGold() + change;
            clan.setGold(newBalance);
            clanService.save(clan);
        }
    }

    public void delete(Transaction transaction) {
        transactionDAO.delete(transaction);
    }

}
