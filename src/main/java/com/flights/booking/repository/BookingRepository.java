package com.flights.booking.repository;

import com.flights.booking.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class BookingRepository {

    private final ConcurrentHashMap<String, Booking> store = new ConcurrentHashMap<>();

    public Booking save(Booking booking) {
        store.put(booking.getBookingId(), booking);
        return booking;
    }

    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(store.get(bookingId));
    }

    public List<Booking> findByFlightNumber(String flightNumber) {
        return store.values().stream()
                .filter(b -> b.getFlightNumber().equals(flightNumber))
                .collect(Collectors.toList());
    }
}
