package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Screen extends Basemodel {
    private String name;
    @Enumerated(EnumType.STRING)
    private ScreenType screenType;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Theatre theatre;
}
