package com.oficinaapp.oficina_app.domain.exception;

public class InsufficientStockException extends DomainException {

    public InsufficientStockException(String productName, int available) {
        super("Only " + available + " unit(s) of " + productName + " left in stock.");
    }
}
