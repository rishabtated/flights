package com.flights.booking.dto;

import java.time.LocalDateTime;

public class FlightResponse {

    private String flightNumber;
    private String origin;
    private String destination;
    private int totalSeats;
    private int availableSeats;
    private LocalDateTime departureTime;

    public FlightResponse(String flightNumber, String origin, String destination,
                          int totalSeats, int availableSeats, LocalDateTime departureTime) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public LocalDateTime getDepartureTime() { return departureTime; }
}
