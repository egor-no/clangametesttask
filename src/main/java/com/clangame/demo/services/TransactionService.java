package com.clangame.demo.services;

import com.clangame.demo.data.dao.TaskTransactionDAO;
import com.clangame.demo.data.dao.TransactionDAO;
import com.clangame.demo.data.dao.UserTransactionDAO;
import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.entities.UserAddGoldTransaction;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TransactionService {

    @Inject
    private TransactionDAO transactionDAO;

    @Inject
    private TaskTransactionDAO taskTransactionDAO;

    @Inject
    private UserTransactionDAO userTransactionDAO;

    @Inject
    private ClanService clanService;

    public Transaction get(long transactionId) {
        return transactionDAO.get(transactionId).orElse(null);
    }

    public TaskTransaction getTaskTransaction(long transactionId) {
        return taskTransactionDAO.get(transactionId).orElse(null);
    }

    public UserAddGoldTransaction getUserTransaction(long transactionId) {
        return userTransactionDAO.get(transactionId).orElse(null);
    }

    public List<Transaction> getAllByClan(long clanId) {
        return transactionDAO.getAllByClanId(clanId);
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

    public void delete(long transactionId) {
        transactionDAO.delete(transactionId);
    }

}
