package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
