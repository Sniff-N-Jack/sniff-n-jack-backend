package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidActivityNameException extends Exception {
    public InvalidActivityNameException(String name) {
        super("Activity with name " + name + " does not exist.");
    }

    public InvalidActivityNameException(List<String> names) {
        super("Activities with names [" + String.join(", ", names) + "] do not exist.");
    }
}
