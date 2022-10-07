package clangame.data.entities;

import clangame.data.tools.GoldSource;
import lombok.Data;

@Data
public class UserAddGoldTransaction {

    private Transaction goldTransaction;
    private long userId;

    public UserAddGoldTransaction() {
        goldTransaction = new Transaction();
        goldTransaction.setSource(GoldSource.USER_ADD);
    }
}
