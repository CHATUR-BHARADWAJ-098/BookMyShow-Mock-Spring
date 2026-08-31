package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SeatsRepository extends JpaRepository<Seats, UUID> {
    List<Seats> findByIdInAndScreenId(Collection<UUID> ids, UUID screenId);
    List<Seats> findByScreenId(UUID screenId);
}
