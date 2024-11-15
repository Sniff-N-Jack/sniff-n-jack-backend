package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCityException extends Exception {
    public InvalidCityException(String city) {
        super("City " + city + " is not part of the instructor's list of cities");
    }
}
