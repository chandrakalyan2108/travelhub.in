package com.travelhub.controller;

import com.travelhub.model.Train;
import com.travelhub.repository.TrainRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrainController {

    private final TrainRepository trainRepository;

    public TrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @GetMapping("/trains")
    public String list(@RequestParam(required = false) String source,
                        @RequestParam(required = false) String destination,
                        @RequestParam(required = false) String date,
                        Model model) {
        List<Train> trains;
        if (source != null && !source.isBlank() && destination != null && !destination.isBlank()) {
            trains = trainRepository.findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCase(source, destination);
        } else {
            trains = trainRepository.findAll();
        }
        model.addAttribute("trains", trains);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("date", date);
        return "trains";
    }
}
