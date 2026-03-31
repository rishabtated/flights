package com.flights.booking.exception;

public class OverbookingException extends RuntimeException {
    public OverbookingException(String flightNumber, int requested, int available) {
        super(String.format(
            "Cannot book %d seat(s) on flight %s — only %d seat(s) available",
            requested, flightNumber, available
        ));
    }
}
