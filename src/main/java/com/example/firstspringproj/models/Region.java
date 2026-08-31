package com.example.firstspringproj.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Region extends Basemodel {
    @Column(nullable = false, unique = true)
    private String name;
}
