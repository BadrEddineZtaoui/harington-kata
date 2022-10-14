package com.harington.kata.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ErrorMessage implements Serializable {

    private String errorMessage;
    private LocalDateTime date;

    public ErrorMessage(String errorMessage, LocalDateTime date) {
        this.errorMessage = errorMessage;
        this.date = date;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
