package com.flights.booking.service;

import com.flights.booking.dto.CreateFlightRequest;
import com.flights.booking.dto.FlightResponse;
import com.flights.booking.exception.FlightAlreadyExistsException;
import com.flights.booking.exception.FlightNotFoundException;
import com.flights.booking.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class FlightServiceTest {

    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightService(new FlightRepository());
    }

    private CreateFlightRequest buildRequest(String flightNumber) {
        CreateFlightRequest req = new CreateFlightRequest();
        req.setFlightNumber(flightNumber);
        req.setOrigin("JFK");
        req.setDestination("LAX");
        req.setTotalSeats(100);
        req.setDepartureTime(LocalDateTime.now().plusDays(1));
        return req;
    }

    @Test
    void createFlight_success() {
        FlightResponse response = flightService.createFlight(buildRequest("AA123"));
        assertThat(response.getFlightNumber()).isEqualTo("AA123");
        assertThat(response.getAvailableSeats()).isEqualTo(100);
    }

    @Test
    void createFlight_duplicateThrows() {
        flightService.createFlight(buildRequest("AA123"));
        assertThatThrownBy(() -> flightService.createFlight(buildRequest("AA123")))
                .isInstanceOf(FlightAlreadyExistsException.class);
    }

    @Test
    void getFlight_notFoundThrows() {
        assertThatThrownBy(() -> flightService.getFlight("ZZ999"))
                .isInstanceOf(FlightNotFoundException.class);
    }

    @Test
    void getAllFlights_returnsAll() {
        flightService.createFlight(buildRequest("AA111"));
        flightService.createFlight(buildRequest("AA222"));
        assertThat(flightService.getAllFlights()).hasSize(2);
    }
}
