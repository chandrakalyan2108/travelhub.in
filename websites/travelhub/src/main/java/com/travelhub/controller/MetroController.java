package com.travelhub.controller;

import com.travelhub.model.MetroRoute;
import com.travelhub.repository.MetroRouteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MetroController {

    private final MetroRouteRepository metroRouteRepository;

    public MetroController(MetroRouteRepository metroRouteRepository) {
        this.metroRouteRepository = metroRouteRepository;
    }

    @GetMapping("/metro")
    public String list(@RequestParam(required = false) String city,
                        @RequestParam(required = false) String source,
                        @RequestParam(required = false) String destination,
                        Model model) {
        List<MetroRoute> routes;
        if (source != null && !source.isBlank() && destination != null && !destination.isBlank()) {
            routes = metroRouteRepository.findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(source, destination);
        } else if (city != null && !city.isBlank()) {
            routes = metroRouteRepository.findByCityContainingIgnoreCase(city);
        } else {
            routes = metroRouteRepository.findAll();
        }
        model.addAttribute("routes", routes);
        model.addAttribute("city", city);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        return "metro";
    }
}
