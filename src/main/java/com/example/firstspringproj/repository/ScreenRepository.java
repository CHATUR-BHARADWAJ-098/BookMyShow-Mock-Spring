package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface ScreenRepository extends JpaRepository<Screen, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Screen s where s.id = :id")
    Optional<Screen> findByIdForUpdate(@Param("id") UUID id);
}
