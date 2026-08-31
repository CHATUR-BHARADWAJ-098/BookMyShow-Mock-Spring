package com.example.firstspringproj.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record CreateBookingRequest(
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotNull UUID screenId,
        @NotNull @Future LocalDateTime showTime,
        @NotEmpty Set<UUID> seatIds) {
}
