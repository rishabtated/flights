package com.flights.booking.service;

import com.flights.booking.dto.BookingResponse;
import com.flights.booking.dto.CreateBookingRequest;
import com.flights.booking.exception.BookingAlreadyCancelledException;
import com.flights.booking.exception.BookingNotFoundException;
import com.flights.booking.exception.OverbookingException;
import com.flights.booking.model.Booking;
import com.flights.booking.model.BookingStatus;
import com.flights.booking.model.Flight;
import com.flights.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final FlightService flightService;
    private final BookingRepository bookingRepository;

    public BookingService(FlightService flightService, BookingRepository bookingRepository) {
        this.flightService = flightService;
        this.bookingRepository = bookingRepository;
    }

    public BookingResponse createBooking(String flightNumber, CreateBookingRequest request) {
        Flight flight = flightService.getFlightOrThrow(flightNumber);

        // Atomic seat reservation — prevents overbooking
        boolean reserved = flight.reserveSeats(request.getSeats());
        if (!reserved) {
            throw new OverbookingException(flightNumber, request.getSeats(), flight.getAvailableSeats());
        }

        Booking booking = new Booking(
                flightNumber,
                request.getPassengerName(),
                request.getPassengerEmail(),
                request.getSeats()
        );
        bookingRepository.save(booking);
        return toResponse(booking);
    }

    public void cancelBooking(String flightNumber, String bookingId) {
        Flight flight = flightService.getFlightOrThrow(flightNumber);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        if (!booking.getFlightNumber().equals(flightNumber)) {
            throw new BookingNotFoundException(bookingId);
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException(bookingId);
        }

        booking.cancel();
        flight.releaseSeats(booking.getSeats());
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingId(),
                booking.getFlightNumber(),
                booking.getPassengerName(),
                booking.getPassengerEmail(),
                booking.getSeats(),
                booking.getBookedAt(),
                booking.getStatus()
        );
    }
}
