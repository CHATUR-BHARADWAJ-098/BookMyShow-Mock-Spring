package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coupon extends Basemodel {
    private String code;
    private double discountPercent;
    private String description;
}