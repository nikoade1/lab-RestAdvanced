package com.epam.esm.exceptions;

public class MoneyNotEnoughException extends RuntimeException {
    public MoneyNotEnoughException(String message) {
        super(message);
    }
}
