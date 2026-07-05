package com.travelhub.repository;

import com.travelhub.model.MetroRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MetroRouteRepository extends JpaRepository<MetroRoute, Long> {
    List<MetroRoute> findByCityContainingIgnoreCase(String city);
    List<MetroRoute> findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(String source, String destination);
}
