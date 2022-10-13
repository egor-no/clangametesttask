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
    private UserTransactionDAO transactionDAO;

    public synchronized UserAddGoldTransaction addGoldToClan(long userId, long clanId, int gold) {
        //if gold > 0
        Transaction transaction = new Transaction();
        transaction.setSuccessful(true);
        transaction.setSource(GoldSource.USER_ADD);
        transaction.setClanId(clanId);
        transaction.setDelta(gold);
        UserAddGoldTransaction userTransaction = new UserAddGoldTransaction();
        userTransaction.setTransaction(transaction);
        userTransaction.setUserId(userId);
        boolean success = transactionDAO.saveAndEditRelatedClanDAO(userTransaction);

//        int newBalance = clan.getGold() + gold;
//        clan.setGold(newBalance);
//        clanService.update(clan);
        if (success)
            return userTransaction;
        else
            return null;
          //  throw new RollbackTransactionException();
    }


}
