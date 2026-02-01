package com.Pranjal.BookingService.Service;
import com.Pranjal.BookingService.Entity.Booking;
import com.Pranjal.BookingService.Repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.Pranjal.BookingService.DTO.TrainDTO;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    public BookingService(BookingRepository bookingRepository, @Lazy RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }



    // MAIN: book ticket safely
    public Booking bookTicket(Booking booking) {

        // 1️⃣ Validate user exists
        Boolean userExists = restTemplate.getForObject(
                "http://user-service:8083/users/exists/" + booking.getUserId(), Boolean.class);
        if (userExists == null || !userExists) {
            throw new RuntimeException("User not found");
        }

        // 2️⃣ Validate train exists
        TrainDTO train = restTemplate.getForObject(
                "http://train-service:8081/trains/" + booking.getTrainId(), TrainDTO.class);
        if (train == null) {
            throw new RuntimeException("Train not found");
        }

        // 3️⃣ Validate seat availability
        if (train.getAvailableSeats() < booking.getSeatsBooked()) {
            throw new RuntimeException("Not enough seats available");
        }

        // 4️⃣ Decrement seats
        Boolean seatDecremented = restTemplate.postForObject(
                "http://train-service:8081/trains/decrement/" + booking.getTrainId() + "?seats=" + booking.getSeatsBooked(),
                null, Boolean.class);
        if (seatDecremented == null || !seatDecremented) {
            throw new RuntimeException("Failed to decrement seats, booking aborted");
        }

        // 5️⃣ Save booking
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }


    public Optional<Booking> getBookingById(Long id) { return bookingRepository.findById(id); }

    public List<Booking> getAllBookings() { return bookingRepository.findAll(); }
}

