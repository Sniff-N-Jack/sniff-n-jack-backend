package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String email) {
        super("No user found with email: " + email);
    }

    public UserNotFoundException(Long id) {
        super("No user found with id: " + id);
    }
}
