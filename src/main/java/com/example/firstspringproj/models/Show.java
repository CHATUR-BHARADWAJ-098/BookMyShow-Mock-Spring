package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Show extends Basemodel {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Screen screen;

    private LocalDate showDate;
    private LocalTime showTime;
    private double price;
    private String formatType;
}