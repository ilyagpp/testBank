package com.example.TestTaskBank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AmountException extends RuntimeException{

    public AmountException(String s) {
        super(s);
    }
}
