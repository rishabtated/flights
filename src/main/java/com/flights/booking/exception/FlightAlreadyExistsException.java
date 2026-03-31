package com.flights.booking.exception;

public class FlightAlreadyExistsException extends RuntimeException {
    public FlightAlreadyExistsException(String flightNumber) {
        super("Flight already exists: " + flightNumber);
    }
}
