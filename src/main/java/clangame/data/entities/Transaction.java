package clangame.data.entities;

import clangame.data.tools.GoldSource;
import lombok.Data;

//Сущность "Транзакция" для отслеживания
@Data
public class Transaction {

    private long id;
    private int delta; //на сколько золота меняется "счёт"
    private GoldSource source;

    private boolean successful;
}