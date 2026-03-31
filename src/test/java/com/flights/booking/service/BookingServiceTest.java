package com.flights.booking.service;

import com.flights.booking.dto.CreateBookingRequest;
import com.flights.booking.dto.CreateFlightRequest;
import com.flights.booking.dto.BookingResponse;
import com.flights.booking.exception.BookingAlreadyCancelledException;
import com.flights.booking.exception.FlightNotFoundException;
import com.flights.booking.exception.OverbookingException;
import com.flights.booking.model.BookingStatus;
import com.flights.booking.repository.BookingRepository;
import com.flights.booking.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class BookingServiceTest {

    private BookingService bookingService;
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        FlightRepository flightRepository = new FlightRepository();
        flightService = new FlightService(flightRepository);
        bookingService = new BookingService(flightService, new BookingRepository());

        // Seed a test flight with 5 seats
        CreateFlightRequest f = new CreateFlightRequest();
        f.setFlightNumber("AA123");
        f.setOrigin("JFK");
        f.setDestination("LAX");
        f.setTotalSeats(5);
        f.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightService.createFlight(f);
    }

    private CreateBookingRequest bookingRequest(int seats) {
        CreateBookingRequest req = new CreateBookingRequest();
        req.setPassengerName("John Doe");
        req.setPassengerEmail("john@example.com");
        req.setSeats(seats);
        return req;
    }

    @Test
    void createBooking_success() {
        BookingResponse resp = bookingService.createBooking("AA123", bookingRequest(2));
        assertThat(resp.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
        assertThat(resp.getSeats()).isEqualTo(2);
        assertThat(flightService.getFlight("AA123").getAvailableSeats()).isEqualTo(3);
    }

    @Test
    void createBooking_overbookingPrevented() {
        bookingService.createBooking("AA123", bookingRequest(5));
        assertThatThrownBy(() -> bookingService.createBooking("AA123", bookingRequest(1)))
                .isInstanceOf(OverbookingException.class);
    }

    @Test
    void createBooking_flightNotFound() {
        assertThatThrownBy(() -> bookingService.createBooking("ZZ999", bookingRequest(1)))
                .isInstanceOf(FlightNotFoundException.class);
    }

    @Test
    void cancelBooking_success_releasesSeats() {
        BookingResponse booking = bookingService.createBooking("AA123", bookingRequest(3));
        assertThat(flightService.getFlight("AA123").getAvailableSeats()).isEqualTo(2);

        bookingService.cancelBooking("AA123", booking.getBookingId());
        assertThat(flightService.getFlight("AA123").getAvailableSeats()).isEqualTo(5);
    }

    @Test
    void cancelBooking_alreadyCancelledThrows() {
        BookingResponse booking = bookingService.createBooking("AA123", bookingRequest(1));
        bookingService.cancelBooking("AA123", booking.getBookingId());

        assertThatThrownBy(() -> bookingService.cancelBooking("AA123", booking.getBookingId()))
                .isInstanceOf(BookingAlreadyCancelledException.class);
    }

    @Test
    void cancelledSeats_allowRebooking() {
        BookingResponse booking = bookingService.createBooking("AA123", bookingRequest(5));
        bookingService.cancelBooking("AA123", booking.getBookingId());

        // After cancellation, all seats should be bookable again
        BookingResponse rebooked = bookingService.createBooking("AA123", bookingRequest(5));
        assertThat(rebooked.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
    }
}
