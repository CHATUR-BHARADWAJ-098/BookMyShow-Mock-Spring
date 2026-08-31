package com.example.firstspringproj.controller;

import com.example.firstspringproj.dto.CreateBookingRequest;
import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.service.BookingResponse;
import com.example.firstspringproj.service.BookingService;
import com.example.firstspringproj.service.ScreenSeatResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings")
    public List<BookingResponse> getBookingsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return bookingService.getBookingsForDate(date);
    }

    @GetMapping("/screens")
    public List<ScreenSeatResponse> getScreens() {
        return bookingService.getScreensWithSeats();
    }

    @PostMapping("/bookings")
    public BookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {
        Booking booking = bookingService.create(request);
        return BookingResponse.from(booking);
    }
}
