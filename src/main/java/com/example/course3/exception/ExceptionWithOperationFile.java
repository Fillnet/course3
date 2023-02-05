package com.example.course3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ExceptionWithOperationFile extends RuntimeException {
    public ExceptionWithOperationFile(String message) {
        super(message);
    }
}