package com.Pranjal.TrainService.Repository;
import com.Pranjal.TrainService.Entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> { }
