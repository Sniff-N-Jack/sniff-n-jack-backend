package com.soen342.sniffnjack.Exceptions;

public class ClientAlreadyBookedOfferingException extends Exception {
    public ClientAlreadyBookedOfferingException() {
        super("Client has already booked this offering");
    }
}
