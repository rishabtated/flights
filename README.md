# Flight Booking API

A Spring Boot REST API for booking flight tickets. In-memory storage, no auth, no database required.

---

## API Endpoints

### Flights

| Method | Endpoint | Description | Success |
|--------|----------|-------------|---------|
| `POST` | `/api/v1/flights` | Register a new flight | `201 Created` |
| `GET` | `/api/v1/flights` | List all flights | `200 OK` |
| `GET` | `/api/v1/flights/{flightNumber}` | Get flight details + available seats | `200 OK` |

### Bookings

| Method | Endpoint | Description | Success |
|--------|----------|-------------|---------|
| `POST` | `/api/v1/flights/{flightNumber}/bookings` | Book seats on a flight | `201 Created` |
| `DELETE` | `/api/v1/flights/{flightNumber}/bookings/{bookingId}` | Cancel a booking | `204 No Content` |

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| `201` | Resource created successfully |
| `204` | Booking cancelled, no content returned |
| `400` | Validation error — check request body |
| `404` | Flight or booking not found |
| `409` | Conflict — duplicate flight, or not enough seats (overbooking prevented), or booking already cancelled |

---

## Example Requests

### Register a Flight
```bash
POST /api/v1/flights
Content-Type: application/json

{
  "flightNumber": "AA123",
  "origin": "JFK",
  "destination": "LAX",
  "totalSeats": 150,
  "departureTime": "2026-12-01T10:00:00"
}
```

### Book Seats
```bash
POST /api/v1/flights/AA123/bookings
Content-Type: application/json

{
  "passengerName": "Jane Smith",
  "passengerEmail": "jane@example.com",
  "seats": 2
}
```

Response:
```json
{
  "bookingId": "3f6b9c1e-...",
  "flightNumber": "AA123",
  "passengerName": "Jane Smith",
  "passengerEmail": "jane@example.com",
  "seats": 2,
  "bookedAt": "2026-03-31T10:00:00",
  "status": "CONFIRMED"
}
```

### Cancel a Booking
```bash
DELETE /api/v1/flights/AA123/bookings/3f6b9c1e-...
```
Returns `204 No Content`. Seats are released back to the flight.

---

## Running the Application

```bash
./mvnw spring-boot:run
```

Or with Maven installed:
```bash
mvn spring-boot:run
```

Server starts on **http://localhost:8080**

## Running Tests

```bash
mvn test
```
