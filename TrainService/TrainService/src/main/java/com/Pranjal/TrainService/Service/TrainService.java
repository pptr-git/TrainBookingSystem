package com.Pranjal.TrainService.Service;

import com.Pranjal.TrainService.Entity.Train;
import com.Pranjal.TrainService.Repository.TrainRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {
    private final TrainRepository trainRepository;
    public TrainService(TrainRepository trainRepository) { this.trainRepository = trainRepository; }

    public Train addTrain(Train train) {
        train.setAvailableSeats(train.getTotalSeats());
        return trainRepository.save(train);
    }

    public Optional<Train> getTrainById(Long id) { return trainRepository.findById(id); }

    public List<Train> getAllTrains() { return trainRepository.findAll(); }

    public Train updateTrain(Train train) { return trainRepository.save(train); }
    // NEW: decrement seats safely
    @Transactional
    public boolean decrementSeats(Long trainId, int seats) {
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) return false;

        Train train = optionalTrain.get();
        if (train.getAvailableSeats() < seats) return false;

        train.setAvailableSeats(train.getAvailableSeats() - seats);
        trainRepository.save(train);
        return true;
    }

    public void deleteTrain(Long id) {
        if (!trainRepository.existsById(id)) {
            throw new EntityNotFoundException("Train not found with id: " + id);
        }
        trainRepository.deleteById(id);
    }


}

