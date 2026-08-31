package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking extends Basemodel {
    private String customerName;
    private String customerMobile;
    private String movieTitle;
    private LocalDateTime showTime;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Screen screen;
    @ManyToMany
    @JoinTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "seat_id"))
    private Set<Seats> seats = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private String bookingId;
    private double totalAmount;
    private String qrCodeText;
}
