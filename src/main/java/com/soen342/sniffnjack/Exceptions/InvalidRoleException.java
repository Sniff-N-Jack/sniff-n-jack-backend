package com.soen342.sniffnjack.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class InvalidRoleException extends Exception {
    public InvalidRoleException(String email, String role, String expectedRole) {
        super("User with email " + email + " has role " + role + " but expected role " + expectedRole);
    }
}
