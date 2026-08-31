package com.example.firstspringproj.service;

import java.util.List;
import java.util.UUID;

public record ScreenSeatResponse(
        UUID id,
        String screenName,
        String theatreName,
        String regionName,
        String screenType,
        List<SeatOption> seats
) {
    public record SeatOption(UUID id, String seatNumber) {
    }
}
