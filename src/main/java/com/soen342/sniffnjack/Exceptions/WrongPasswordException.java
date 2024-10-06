package com.soen342.sniffnjack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException() {
        super("Wrong password");
    }
}
