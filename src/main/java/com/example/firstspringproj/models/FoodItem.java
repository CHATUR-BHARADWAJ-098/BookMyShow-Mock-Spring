package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FoodItem extends Basemodel {
    private String name;
    private String category;
    private double price;
    private String description;
}