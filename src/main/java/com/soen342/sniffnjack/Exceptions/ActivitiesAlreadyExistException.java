package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActivitiesAlreadyExistException extends Exception {
    public ActivitiesAlreadyExistException(String[] names) {
        super("Activities with names " + String.join(", ", names) + " already exist.");
    }
}
