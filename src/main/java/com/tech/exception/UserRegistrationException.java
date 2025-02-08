package com.tech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserRegistrationException extends Throwable {
    public UserRegistrationException(String ex) {
        super(ex);
    }
}