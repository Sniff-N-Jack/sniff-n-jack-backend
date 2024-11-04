package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCityNameException extends Exception {
    public InvalidCityNameException(String name) {
        super("City with name " + name + " does not exist.");
    }

    public InvalidCityNameException(List<String> names) {
        super("Cities with names [" + String.join(", ", names) + "] do not exist.");
    }
}
