package com.soen342.sniffnjack.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class ActivityAlreadyExistsException extends Exception {
    public ActivityAlreadyExistsException(String name) {
        super("Activity with name " + name + " already exists");
    }

    public ActivityAlreadyExistsException(List<String> names) {
        super("Activities with names " + String.join(", ", names) + " already exist");
    }
}
