package com.example.firstspringproj.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class Basemodel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Instant createdDate;
    private Instant updatedDate;

    @PrePersist
    void createTimestamps() {
        createdDate = Instant.now();
        updatedDate = createdDate;
    }

    @PreUpdate
    void updateTimestamp() {
        updatedDate = Instant.now();
    }
}
