package com.travelhub.controller;

import com.travelhub.model.Flight;
import com.travelhub.repository.FlightRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FlightController {

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/flights")
    public String list(@RequestParam(required = false) String source,
                        @RequestParam(required = false) String destination,
                        @RequestParam(required = false) String date,
                        Model model) {
        List<Flight> flights;
        if (source != null && !source.isBlank() && destination != null && !destination.isBlank()) {
            flights = flightRepository.findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(source, destination);
        } else {
            flights = flightRepository.findAll();
        }
        model.addAttribute("flights", flights);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("date", date);
        return "flights";
    }
}
