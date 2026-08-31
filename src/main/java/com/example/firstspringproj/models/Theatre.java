package com.example.firstspringproj.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Theatre extends Basemodel {
    private String name;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Region region;
}
