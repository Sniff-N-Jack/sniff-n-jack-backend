package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CityAlreadyExistsException extends Exception {
    public CityAlreadyExistsException(String name) {
        super("City with name " + name + " already exists.");
    }

    public CityAlreadyExistsException(List<String> names) {
        super("Cities with names " + String.join(", ", names) + " already exist.");
    }
}
