package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLocationException extends Exception {
    public InvalidLocationException() {
        super("Location does not exist");
    }

    public InvalidLocationException(String location) {
        super("Location " + location + " is not part of the instructor's list of locations");
    }
}
