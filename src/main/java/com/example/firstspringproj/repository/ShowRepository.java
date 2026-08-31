package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShowRepository extends JpaRepository<Show, UUID> {
    List<Show> findByMovieIdAndScreen_Theatre_Region_Id(UUID movieId, UUID regionId);
    List<Show> findByMovieIdAndScreen_Theatre_Region_IdOrderByShowDateAscShowTimeAsc(UUID movieId, UUID regionId);
}
