package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomBadRequestException extends Exception {
    public CustomBadRequestException(String message) {
        super(message);
    }
}
