package com.clangame.demo.services;

import com.clangame.demo.data.dao.TaskDAO;
import com.clangame.demo.data.dao.TaskTransactionDAO;
import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.data.entities.Task;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.tools.GoldSource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Random;

@ApplicationScoped
public class TaskService { // какой-то сервис с заданиями

    @Inject
    private ClanService clanService;

    @Inject
    private TaskTransactionDAO transactionDAO;

    @Inject
    private TaskDAO taskDAO;

    public Task get(long taskId) {
        return taskDAO.get(taskId).orElse(null);
    }

    public void save(Task task) {
        taskDAO.save(task);
    }

    public void update(Task task) {
        taskDAO.update(task);
    }

    public void completeTask(long clanId, long taskId) {
        boolean isAttemptSuccessful = isTaskComplete(clanId, taskId);
        int delta = taskDAO.get(taskId).get().getGoldGiven();

        if (isAttemptSuccessful) {
             Clan clan = clanService.get(clanId);
             int newBalance = clan.getGold() + delta;
             clan.setGold(newBalance);
             clanService.save(clan);
        }

        Transaction transaction = new Transaction();
        transaction.setSuccessful(isAttemptSuccessful);
        transaction.setSource(GoldSource.TASK);
        transaction.setClanId(clanId);
        transaction.setDelta(delta);
        TaskTransaction taskTransaction = new TaskTransaction();
        taskTransaction.setTransaction(transaction);
        taskTransaction.setTaskId(taskId);
        transactionDAO.save(taskTransaction);
    }

    private boolean isTaskComplete(long clanId, long taskId) {
        //имитируем логику задания
        //у нас логики нет. всё решает рандом
        int random = new Random().nextInt(100);
        if (random<60)
            return true;
        return false;
    }

    public void delete(Task task) {
        taskDAO.delete(task);
    }

}