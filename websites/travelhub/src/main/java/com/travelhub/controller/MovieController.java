package com.travelhub.controller;

import com.travelhub.model.Movie;
import com.travelhub.repository.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public String list(@RequestParam(required = false) String city, Model model) {
        List<Movie> movies = (city == null || city.isBlank())
                ? movieRepository.findAll()
                : movieRepository.findByCityContainingIgnoreCase(city);
        model.addAttribute("movies", movies);
        model.addAttribute("city", city);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        return "movie-detail";
    }
}
