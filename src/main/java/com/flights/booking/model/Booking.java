package com.flights.booking.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {

    private final String bookingId;
    private final String flightNumber;
    private final String passengerName;
    private final String passengerEmail;
    private final int seats;
    private final LocalDateTime bookedAt;
    private BookingStatus status;

    public Booking(String flightNumber, String passengerName,
                   String passengerEmail, int seats) {
        this.bookingId = UUID.randomUUID().toString();
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.seats = seats;
        this.bookedAt = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
    }

    public String getBookingId() { return bookingId; }
    public String getFlightNumber() { return flightNumber; }
    public String getPassengerName() { return passengerName; }
    public String getPassengerEmail() { return passengerEmail; }
    public int getSeats() { return seats; }
    public LocalDateTime getBookedAt() { return bookedAt; }
    public BookingStatus getStatus() { return status; }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }
}
