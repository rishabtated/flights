package com.flights.booking.dto;

import com.flights.booking.model.BookingStatus;
import java.time.LocalDateTime;

public class BookingResponse {

    private String bookingId;
    private String flightNumber;
    private String passengerName;
    private String passengerEmail;
    private int seats;
    private LocalDateTime bookedAt;
    private BookingStatus status;

    public BookingResponse(String bookingId, String flightNumber, String passengerName,
                           String passengerEmail, int seats,
                           LocalDateTime bookedAt, BookingStatus status) {
        this.bookingId = bookingId;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.seats = seats;
        this.bookedAt = bookedAt;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public String getFlightNumber() { return flightNumber; }
    public String getPassengerName() { return passengerName; }
    public String getPassengerEmail() { return passengerEmail; }
    public int getSeats() { return seats; }
    public LocalDateTime getBookedAt() { return bookedAt; }
    public BookingStatus getStatus() { return status; }
}
