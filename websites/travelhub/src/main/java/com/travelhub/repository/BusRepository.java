package com.travelhub.repository;

import com.travelhub.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(String source, String destination);
}
