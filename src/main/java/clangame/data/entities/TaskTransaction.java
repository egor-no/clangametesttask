package clangame.data.entities;

import clangame.data.tools.GoldSource;

public class TaskTransaction {

    private Transaction goldTransaction;
    private long taskId;

    public TaskTransaction() {
        goldTransaction = new Transaction();
        goldTransaction.setSource(GoldSource.USER_ADD);
    }
}
