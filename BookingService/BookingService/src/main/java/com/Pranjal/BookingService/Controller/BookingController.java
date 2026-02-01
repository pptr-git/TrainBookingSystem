package com.Pranjal.BookingService.Controller;


import com.Pranjal.BookingService.Entity.Booking;
import com.Pranjal.BookingService.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking saved = bookingService.bookTicket(booking);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

@GetMapping("/{id}")
public ResponseEntity<?> getBookingById(@PathVariable Long id) {
    return bookingService.getBookingById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Booking not found with id: " + id))
            );
}

@GetMapping
public ResponseEntity<?> getAllUsers() {
    List<Booking> bookings = bookingService.getAllBookings();

    if (bookings.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "No Bookings found"));
    }

    return ResponseEntity.ok(bookings);
}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking deleted successfully");
    }

}

