package com.travelhub.controller;

import com.travelhub.model.Hotel;
import com.travelhub.repository.HotelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HotelController {

    private final HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/hotels")
    public String list(@RequestParam(required = false) String city, Model model) {
        List<Hotel> hotels = (city == null || city.isBlank())
                ? hotelRepository.findAll()
                : hotelRepository.findByCityContainingIgnoreCase(city);
        model.addAttribute("hotels", hotels);
        model.addAttribute("city", city);
        return "hotels";
    }

    @GetMapping("/hotels/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow();
        model.addAttribute("hotel", hotel);
        return "hotel-detail";
    }
}
