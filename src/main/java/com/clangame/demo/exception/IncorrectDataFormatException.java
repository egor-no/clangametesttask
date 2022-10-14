package com.clangame.demo.exception;

public class IncorrectDataFormatException extends Exception {

    public IncorrectDataFormatException(String message) {
        super("Verify that your data is correct" +
                (message.isEmpty() ? "" : "Details: "+message));
    }

}
