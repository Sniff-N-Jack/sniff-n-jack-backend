package com.soen342.sniffnjack.Exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserDisabledException extends AuthenticationException {
    public UserDisabledException() {
        super("User is currently disabled");
    }
}
