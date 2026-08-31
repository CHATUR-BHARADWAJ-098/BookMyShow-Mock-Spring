package com.example.firstspringproj.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateBookingRequest(
        @NotBlank String customerName,
        @NotBlank @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must contain exactly 10 digits") String customerMobile,
        @NotBlank String movieTitle,
        @NotNull UUID screenId,
        @NotNull @Future LocalDateTime showTime,
        @NotEmpty Set<UUID> seatIds) {
}
