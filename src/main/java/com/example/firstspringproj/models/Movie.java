package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie extends Basemodel {
    private String title;
    private String language;
    private String genre;
    private String duration;
    private String certificate;
    private String posterUrl;
    @Lob
    private String description;
    private double rating;
}