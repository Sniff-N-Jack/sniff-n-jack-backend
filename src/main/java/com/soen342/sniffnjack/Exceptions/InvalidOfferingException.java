package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOfferingException extends Exception {
    public InvalidOfferingException() {
        super("Offering does not exist");
    }
}
