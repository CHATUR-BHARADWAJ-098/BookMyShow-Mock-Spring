package com.example.firstspringproj.service;

import com.example.firstspringproj.dto.CreateBookingRequest;
import com.example.firstspringproj.repository.BookingRepository;
import com.example.firstspringproj.repository.ScreenRepository;
import com.example.firstspringproj.repository.SeatsRepository;
import jakarta.transaction.Transactional;
import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.models.BookingStatus;
import com.example.firstspringproj.models.Screen;
import com.example.firstspringproj.models.Seats;
import org.springframework.stereotype.Service;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ScreenRepository screenRepository;
    private final SeatsRepository seatsRepository;

    public BookingService(BookingRepository bookingRepository, ScreenRepository screenRepository, SeatsRepository seatsRepository) {
        this.bookingRepository = bookingRepository;
        this.screenRepository = screenRepository;
        this.seatsRepository = seatsRepository;
    }

    @Transactional
    public Booking create(CreateBookingRequest request) {
        Screen screen = screenRepository.findByIdForUpdate(request.screenId())
                .orElseThrow(() -> new IllegalArgumentException("Screen not found"));
        List<Seats> requestedSeats = seatsRepository.findByIdInAndScreenId(request.seatIds(), screen.getId());
        if (requestedSeats.size() != request.seatIds().size()) {
            throw new IllegalArgumentException("One or more seats do not belong to this screen");
        }
        Set<UUID> unavailable = bookedSeatIds(screen.getId(), request.showTime());
        if (request.seatIds().stream().anyMatch(unavailable::contains)) {
            throw new IllegalStateException("At least one requested seat is already booked");
        }
        Booking booking = new Booking();
        booking.setCustomerName(request.customerName());
        booking.setCustomerEmail(request.customerEmail());
        booking.setScreen(screen);
        booking.setShowTime(request.showTime());
        booking.setSeats(new HashSet<>(requestedSeats));
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    private Set<UUID> bookedSeatIds(UUID screenId, java.time.LocalDateTime showTime) {
        return bookingRepository.findByScreenIdAndShowTimeAndBookingStatusIn(screenId, showTime,
                        EnumSet.of(BookingStatus.PENDING, BookingStatus.CONFIRMED))
                .stream().flatMap(booking -> booking.getSeats().stream()).map(Seats::getId)
                .collect(java.util.stream.Collectors.toSet());
    }
}
