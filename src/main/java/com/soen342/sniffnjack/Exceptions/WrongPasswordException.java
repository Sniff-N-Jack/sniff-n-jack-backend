package com.soen342.sniffnjack.Exceptions;

import org.springframework.security.core.AuthenticationException;

public class WrongPasswordException extends AuthenticationException {
    public WrongPasswordException() {
        super("Wrong password");
    }
}
