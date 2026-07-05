package com.travelhub.service;

import com.travelhub.model.*;
import com.travelhub.repository.BookingRepository;
import com.travelhub.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public BookingService(BookingRepository bookingRepository, PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    public Booking createBooking(BookingType type, Long itemId, String itemName, String itemDetails,
                                  String travelDate, Integer quantity, Double amount,
                                  String customerName, String customerEmail, String customerPhone) {
        Booking booking = new Booking();
        booking.setReference(generateReference());
        booking.setType(type);
        booking.setItemId(itemId);
        booking.setItemName(itemName);
        booking.setItemDetails(itemDetails);
        booking.setTravelDate(travelDate);
        booking.setQuantity(quantity);
        booking.setAmount(amount);
        booking.setCustomerName(customerName);
        booking.setCustomerEmail(customerEmail);
        booking.setCustomerPhone(customerPhone);
        booking.setStatus(BookingStatus.PENDING_PAYMENT);
        booking.setCreatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public Optional<Booking> findByReference(String reference) {
        return bookingRepository.findByReference(reference);
    }

    public Payment processPayment(String bookingReference, String method) {
        Booking booking = bookingRepository.findByReference(bookingReference)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingReference));

        Payment payment = new Payment();
        payment.setBookingReference(bookingReference);
        payment.setMethod(method);
        payment.setTransactionId("TXN" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        payment.setAmount(booking.getAmount());
        payment.setStatus("SUCCESS");
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return payment;
    }

    public Optional<Payment> findPaymentByReference(String reference) {
        return paymentRepository.findByBookingReference(reference);
    }

    private String generateReference() {
        return "TH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
