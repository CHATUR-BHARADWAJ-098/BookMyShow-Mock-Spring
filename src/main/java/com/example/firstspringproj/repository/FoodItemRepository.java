package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {
}
