package com.clangame.demo.services;

import com.clangame.demo.data.dao.UserTransactionDAO;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.entities.UserAddGoldTransaction;
import com.clangame.demo.data.tools.GoldSource;
import com.clangame.demo.exception.IncorrectDataFormatException;
import com.clangame.demo.exception.TransactionIsNotCommittedException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ApplicationScoped
public class UserAddGoldService { // пользователь добавляет золото из собственного кармана

    @Inject
    private UserTransactionDAO transactionDAO;

    public synchronized UserAddGoldTransaction addGoldToClan(long userId,
                                                             long clanId,
                                                             int gold) throws TransactionIsNotCommittedException, IncorrectDataFormatException {
        if (gold <= 0)
            throw new IncorrectDataFormatException(" Gold should be more than zero");

        Transaction transaction = new Transaction();
        transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
        transaction.setSuccessful(canUserAddMoney(clanId, gold));
        transaction.setSource(GoldSource.USER_ADD);
        transaction.setClanId(clanId);
        transaction.setDelta(gold);
        UserAddGoldTransaction userTransaction = new UserAddGoldTransaction();
        userTransaction.setTransaction(transaction);
        userTransaction.setUserId(userId);
        transactionDAO.saveAndEditRelatedClanDAO(userTransaction);
        return userTransaction;
    }

    private boolean canUserAddMoney(long userId, int gold) {
        // здесь можно проверить, может ли юзер перевести столько денег
        // хватает ли у него денег на счету
        // или не отклонил ли банк его карту.... но пока вернём просто тру
        return true;
    }


}
