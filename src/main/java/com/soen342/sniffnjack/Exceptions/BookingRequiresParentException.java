package com.soen342.sniffnjack.Exceptions;

public class BookingRequiresParentException extends Exception {
    public BookingRequiresParentException() {
        super("Client is less than 18 years old, booking requires a parent");
    }
}
