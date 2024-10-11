package com.soen342.sniffnjack.Exceptions;

public class InvalidParentCandidateException extends Exception {
    public InvalidParentCandidateException(String email) {
        super("Invalid parent candidate: " + email + " is under 18 years old.");
    }
}
