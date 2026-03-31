package com.flights.booking.controller;

import com.flights.booking.dto.BookingResponse;
import com.flights.booking.dto.CreateBookingRequest;
import com.flights.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flights/{flightNumber}/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * POST /api/v1/flights/{flightNumber}/bookings
     * Book seats on a flight.
     * Returns 201 Created with booking details on success.
     * Returns 404 if flight not found.
     * Returns 409 if there are not enough seats (overbooking prevented).
     */
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable String flightNumber,
            @Valid @RequestBody CreateBookingRequest request) {
        BookingResponse response = bookingService.createBooking(flightNumber, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * DELETE /api/v1/flights/{flightNumber}/bookings/{bookingId}
     * Cancel an existing booking and release the seats back to the flight.
     * Returns 204 No Content on success.
     * Returns 404 if the flight or booking is not found.
     * Returns 409 if the booking is already cancelled.
     */
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable String flightNumber,
            @PathVariable String bookingId) {
        bookingService.cancelBooking(flightNumber, bookingId);
        return ResponseEntity.noContent().build();
    }
}
