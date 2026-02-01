package com.Pranjal.TrainService.Controller;
import com.Pranjal.TrainService.Entity.Train;
import com.Pranjal.TrainService.Service.TrainService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trains")
public class TrainController {

    private final TrainService trainService;
    public TrainController(TrainService trainService) { this.trainService = trainService; }

    @PostMapping
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        return ResponseEntity.ok(trainService.addTrain(train));
    }
@GetMapping("/{id}")
public ResponseEntity<?> getTrainById(@PathVariable Long id) {
    return trainService.getTrainById(id)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Train not found with id: " + id))
            );
}

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<Train> trains = trainService.getAllTrains();

        if (trains.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No trains found"));
        }

        return ResponseEntity.ok(trains);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Train> updateTrain(@PathVariable Long id, @RequestBody Train train) {
        train.setId(id);
        return ResponseEntity.ok(trainService.updateTrain(train));
    }


    // NEW: decrement seats (called by BookingService)
    @PostMapping("/decrement/{id}")
    public ResponseEntity<Boolean> decrementSeats(@PathVariable Long id, @RequestParam int seats) {
        boolean success = trainService.decrementSeats(id, seats);
        return ResponseEntity.ok(success);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrain(@PathVariable Long id) {
        try {
            trainService.deleteTrain(id);
            return ResponseEntity.ok("Train deleted successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}

