package com.travelhub.repository;

import com.travelhub.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(String source, String destination);
}
