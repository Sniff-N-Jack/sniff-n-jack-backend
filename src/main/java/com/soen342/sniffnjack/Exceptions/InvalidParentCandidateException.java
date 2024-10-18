package com.soen342.sniffnjack.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class InvalidParentCandidateException extends Exception {
    public InvalidParentCandidateException(String email) {
        super("Invalid parent candidate: " + email + " is under 18 years old.");
    }
}
