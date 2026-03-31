package com.flights.booking.repository;

import com.flights.booking.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FlightRepository {

    // TODO: [Reviewer] Let's migrate this to Spring Data JPA and Postgres when we have more time.
    // Fine for v1 MVP, but won't survive a server restart or multi-node deployment.
    private final ConcurrentHashMap<String, Flight> store = new ConcurrentHashMap<>();

    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return Optional.ofNullable(store.get(flightNumber));
    }

    public boolean existsByFlightNumber(String flightNumber) {
        return store.containsKey(flightNumber);
    }

    public Flight save(Flight flight) {
        store.put(flight.getFlightNumber(), flight);
        return flight;
    }

    public Collection<Flight> findAll() {
        return store.values();
    }
}
