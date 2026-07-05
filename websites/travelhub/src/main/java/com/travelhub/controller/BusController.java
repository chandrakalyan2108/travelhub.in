package com.travelhub.controller;

import com.travelhub.model.Bus;
import com.travelhub.repository.BusRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BusController {

    private final BusRepository busRepository;

    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @GetMapping("/buses")
    public String list(@RequestParam(required = false) String source,
                        @RequestParam(required = false) String destination,
                        @RequestParam(required = false) String date,
                        Model model) {
        List<Bus> buses;
        if (source != null && !source.isBlank() && destination != null && !destination.isBlank()) {
            buses = busRepository.findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(source, destination);
        } else {
            buses = busRepository.findAll();
        }
        model.addAttribute("buses", buses);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("date", date);
        return "buses";
    }
}
