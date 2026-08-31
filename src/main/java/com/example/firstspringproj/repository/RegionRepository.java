package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
}
