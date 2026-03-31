package com.flights.booking.model;

import java.time.LocalDateTime;

public class Flight {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final int totalSeats;
    private int availableSeats;
    private final LocalDateTime departureTime;

    public Flight(String flightNumber, String origin, String destination,
                  int totalSeats, LocalDateTime departureTime) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.departureTime = departureTime;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public LocalDateTime getDepartureTime() { return departureTime; }

    /**
     * Reserves the requested number of seats.
     * Returns false if not enough seats are available (overbooking prevention).
     */
    public synchronized boolean reserveSeats(int count) {
        if (count <= 0 || count > availableSeats) {
            return false;
        }
        availableSeats -= count;
        return true;
    }

    /**
     * Releases seats back to the flight (e.g., on cancellation).
     */
    public synchronized void releaseSeats(int count) {
        availableSeats = Math.min(totalSeats, availableSeats + count);
    }
}
