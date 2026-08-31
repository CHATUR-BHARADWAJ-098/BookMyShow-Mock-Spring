package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"screen_id", "seatNumber"}))
@Getter
@Setter
@NoArgsConstructor
public class Seats extends Basemodel {
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Screen screen;
}
