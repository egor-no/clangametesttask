package com.clangame.demo.services;

import com.clangame.demo.data.dao.TaskDAO;
import com.clangame.demo.data.dao.TaskTransactionDAO;
import com.clangame.demo.data.entities.Task;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.data.tools.GoldSource;
import com.clangame.demo.exception.TransactionIsNotCommittedException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
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

    public List<Task> getAll() {
        return taskDAO.getAll();
    }

    public long save(Task task) {
        taskDAO.save(task);
        long id = taskDAO.getAll().size();
        return id;
    }

    public void update(Task task) {
        taskDAO.update(task);
    }

    public TaskTransaction completeTask(long clanId, long taskId) throws TransactionIsNotCommittedException {
        boolean isAttemptSuccessful = isTaskComplete(clanId, taskId);
        int delta = taskDAO.get(taskId).get().getGoldGiven();

        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setSuccessful(isAttemptSuccessful);
        transaction.setSource(GoldSource.TASK);
        transaction.setClanId(clanId);
        transaction.setDelta(delta);
        TaskTransaction taskTransaction = new TaskTransaction();
        taskTransaction.setTransaction(transaction);
        taskTransaction.setTaskId(taskId);

        if (isAttemptSuccessful) {
            transactionDAO.saveAndEditRelatedClan(taskTransaction);
            return taskTransaction;
        } else {
            transactionDAO.save(taskTransaction);
            return null;
        }
    }

    private boolean isTaskComplete(long clanId, long taskId) {
        //имитируем логику задания
        //у нас логики нет. всё решает рандом
        int random = new Random().nextInt(100);
        if (random<99)
            return true;
        return false;
    }

    public void delete(long taskId) {
        taskDAO.delete(taskId);
    }

}