package com.flights.booking.exception;

public class BookingAlreadyCancelledException extends RuntimeException {
    public BookingAlreadyCancelledException(String bookingId) {
        super("Booking " + bookingId + " is already cancelled");
    }
}
