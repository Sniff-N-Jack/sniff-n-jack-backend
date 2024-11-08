package com.soen342.sniffnjack.Exceptions;

public class BookingForOtherUserException extends RuntimeException {
    public BookingForOtherUserException() {
        super("Cannot book for another user that isn't the authenticated user's child");
    }
}
