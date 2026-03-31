package com.flights.booking.service;

import com.flights.booking.dto.CreateFlightRequest;
import com.flights.booking.dto.FlightResponse;
import com.flights.booking.exception.FlightAlreadyExistsException;
import com.flights.booking.exception.FlightNotFoundException;
import com.flights.booking.model.Flight;
import com.flights.booking.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightResponse createFlight(CreateFlightRequest request) {
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new FlightAlreadyExistsException(request.getFlightNumber());
        }
        Flight flight = new Flight(
                request.getFlightNumber(),
                request.getOrigin(),
                request.getDestination(),
                request.getTotalSeats(),
                request.getDepartureTime()
        );
        flightRepository.save(flight);
        return toResponse(flight);
    }

    public FlightResponse getFlight(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber));
        return toResponse(flight);
    }

    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Flight getFlightOrThrow(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber));
    }

    private FlightResponse toResponse(Flight flight) {
        return new FlightResponse(
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getTotalSeats(),
                flight.getAvailableSeats(),
                flight.getDepartureTime()
        );
    }
}
