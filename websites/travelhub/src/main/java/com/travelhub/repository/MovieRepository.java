package com.travelhub.repository;

import com.travelhub.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByCityContainingIgnoreCase(String city);
}
