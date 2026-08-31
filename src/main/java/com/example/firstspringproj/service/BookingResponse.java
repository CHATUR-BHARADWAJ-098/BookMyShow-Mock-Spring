package com.example.firstspringproj.service;

import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.models.Seats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookingResponse(
        UUID id,
        String customerName,
        String customerMobile,
        String movieTitle,
        LocalDateTime showTime,
        UUID screenId,
        String screenName,
        String theatreName,
        String regionName,
        List<String> seatNumbers
) {
    public static BookingResponse from(Booking booking) {
        var screen = booking.getScreen();
        var theatre = screen != null ? screen.getTheatre() : null;
        var region = theatre != null ? theatre.getRegion() : null;

        return new BookingResponse(
                booking.getId(),
                booking.getCustomerName(),
                booking.getCustomerMobile(),
                booking.getMovieTitle(),
                booking.getShowTime(),
                screen != null ? screen.getId() : null,
                screen != null ? screen.getName() : null,
                theatre != null ? theatre.getName() : null,
                region != null ? region.getName() : null,
                booking.getSeats() == null ? List.of() : booking.getSeats().stream()
                        .map(Seats::getSeatNumber)
                        .sorted()
                        .toList()
        );
    }
}
