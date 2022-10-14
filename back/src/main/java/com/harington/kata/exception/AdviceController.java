package com.harington.kata.exception;

import com.harington.kata.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleClientNotFoundException(ClientNotFoundException ex) {
        return new ResponseEntity<>(getExceptionMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(getExceptionMessage(ex.getMessage()),  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(InvalidArgumentException ex) {
        return new ResponseEntity<>(getExceptionMessage(ex.getMessage()),  HttpStatus.BAD_REQUEST);
    }

    private ErrorMessage getExceptionMessage(String message) {
        return new ErrorMessage(message, LocalDateTime.now());
    }
}
