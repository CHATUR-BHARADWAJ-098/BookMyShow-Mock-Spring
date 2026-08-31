package com.example.firstspringproj.repository;

import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.models.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByScreenIdAndShowTimeAndBookingStatusIn(UUID screenId, LocalDateTime showTime, Collection<BookingStatus> statuses);

    List<Booking> findByShowTimeBetweenOrderByShowTimeAsc(LocalDateTime start, LocalDateTime end);
}
