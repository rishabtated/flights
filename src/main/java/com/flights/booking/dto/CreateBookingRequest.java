package com.flights.booking.dto;

import jakarta.validation.constraints.*;

public class CreateBookingRequest {

    @NotBlank(message = "Passenger name is required")
    private String passengerName;

    @NotBlank(message = "Passenger email is required")
    @Email(message = "Must be a valid email address")
    private String passengerEmail;

    @Min(value = 1, message = "Must book at least 1 seat")
    @Max(value = 9, message = "Cannot book more than 9 seats at once")
    private int seats;

    // can take details of all passengers who are travelling
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }
}
