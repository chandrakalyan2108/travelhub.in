package com.travelhub.repository;

import com.travelhub.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(String source, String destination);
}
