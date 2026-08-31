package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TheatreRepository extends JpaRepository<Theatre, UUID> {
}
