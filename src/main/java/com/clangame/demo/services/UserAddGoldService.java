package com.clangame.demo.services;

import com.clangame.demo.data.dao.UserTransactionDAO;
import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.entities.UserAddGoldTransaction;
import com.clangame.demo.data.tools.GoldSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    @Inject
    private ClanService clanService;

    @Inject
    private UserTransactionDAO transactionDAO;

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clanService.get(clanId);
        //if gold > 0
        Transaction transaction = new Transaction();
        transaction.setSuccessful(true);
        transaction.setSource(GoldSource.USER_ADD);
        transaction.setClanId(clanId);
        transaction.setDelta(gold);
        UserAddGoldTransaction userTransaction = new UserAddGoldTransaction();
        userTransaction.setTransaction(transaction);
        userTransaction.setUserId(userId);
        transactionDAO.save(userTransaction);
    }
}
