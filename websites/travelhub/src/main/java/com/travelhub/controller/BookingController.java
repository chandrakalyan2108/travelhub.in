package com.travelhub.controller;

import com.travelhub.model.Booking;
import com.travelhub.model.BookingType;
import com.travelhub.model.Payment;
import com.travelhub.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public String create(@RequestParam BookingType type,
                          @RequestParam(required = false) Long itemId,
                          @RequestParam String itemName,
                          @RequestParam(required = false) String itemDetails,
                          @RequestParam(required = false) String travelDate,
                          @RequestParam(defaultValue = "1") Integer quantity,
                          @RequestParam Double amount,
                          @RequestParam String customerName,
                          @RequestParam String customerEmail,
                          @RequestParam(required = false) String customerPhone) {

        double totalAmount = amount * (quantity == null ? 1 : quantity);
        Booking booking = bookingService.createBooking(type, itemId, itemName, itemDetails, travelDate,
                quantity, totalAmount, customerName, customerEmail, customerPhone);
        return "redirect:/booking/" + booking.getReference() + "/pay";
    }

    @GetMapping("/{reference}/pay")
    public String pay(@PathVariable String reference, Model model) {
        Booking booking = bookingService.findByReference(reference)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        model.addAttribute("booking", booking);
        return "payment";
    }

    @PostMapping("/{reference}/pay")
    public String processPayment(@PathVariable String reference,
                                  @RequestParam String method) {
        bookingService.processPayment(reference, method);
        return "redirect:/booking/" + reference + "/confirmation";
    }

    @GetMapping("/{reference}/confirmation")
    public String confirmation(@PathVariable String reference, Model model) {
        Booking booking = bookingService.findByReference(reference)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Payment payment = bookingService.findPaymentByReference(reference).orElse(null);
        model.addAttribute("booking", booking);
        model.addAttribute("payment", payment);
        return "confirmation";
    }
}
