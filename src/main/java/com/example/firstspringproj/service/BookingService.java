package com.example.firstspringproj.service;

import com.example.firstspringproj.dto.CreateBookingRequest;
import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.models.BookingStatus;
import com.example.firstspringproj.models.Coupon;
import com.example.firstspringproj.models.FoodItem;
import com.example.firstspringproj.models.Movie;
import com.example.firstspringproj.models.Region;
import com.example.firstspringproj.models.Screen;
import com.example.firstspringproj.models.Seats;
import com.example.firstspringproj.models.Show;
import com.example.firstspringproj.repository.BookingRepository;
import com.example.firstspringproj.repository.CouponRepository;
import com.example.firstspringproj.repository.FoodItemRepository;
import com.example.firstspringproj.repository.MovieRepository;
import com.example.firstspringproj.repository.RegionRepository;
import com.example.firstspringproj.repository.ScreenRepository;
import com.example.firstspringproj.repository.SeatsRepository;
import com.example.firstspringproj.repository.ShowRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;
    private final RegionRepository regionRepository;
    private final ScreenRepository screenRepository;
    private final SeatsRepository seatsRepository;
    private final ShowRepository showRepository;
    private final FoodItemRepository foodItemRepository;
    private final CouponRepository couponRepository;

    public BookingService(BookingRepository bookingRepository,
                          MovieRepository movieRepository,
                          RegionRepository regionRepository,
                          ScreenRepository screenRepository,
                          SeatsRepository seatsRepository,
                          ShowRepository showRepository,
                          FoodItemRepository foodItemRepository,
                          CouponRepository couponRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
        this.regionRepository = regionRepository;
        this.screenRepository = screenRepository;
        this.seatsRepository = seatsRepository;
        this.showRepository = showRepository;
        this.foodItemRepository = foodItemRepository;
        this.couponRepository = couponRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Optional<Movie> getMovieById(UUID movieId) {
        return movieRepository.findById(movieId);
    }

    public List<Show> getShowsForMovieAndRegion(UUID movieId, UUID regionId) {
        return showRepository.findByMovieIdAndScreen_Theatre_Region_IdOrderByShowDateAscShowTimeAsc(movieId, regionId);
    }

    public List<FoodItem> getFoodItems() {
        return foodItemRepository.findAll();
    }

    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findByCode(code);
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
        booking.setCustomerMobile(request.customerMobile());
        booking.setMovieTitle(request.movieTitle());
        booking.setScreen(screen);
        booking.setShowTime(request.showTime());
        booking.setSeats(new HashSet<>(requestedSeats));
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        Booking savedBooking = bookingRepository.save(booking);
        savedBooking.getScreen().getTheatre().getRegion().getName();
        savedBooking.getSeats().forEach(seat -> seat.getSeatNumber());
        return savedBooking;
    }

    @Transactional
    public List<BookingResponse> getBookingsForDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return bookingRepository.findByShowTimeBetweenOrderByShowTimeAsc(start, end)
                .stream()
                .map(BookingResponse::from)
                .collect(Collectors.toList());
    }

    public List<ScreenSeatResponse> getScreensWithSeats() {
        return screenRepository.findAll().stream()
                .map(screen -> {
                    var theatre = screen.getTheatre();
                    var region = theatre != null ? theatre.getRegion() : null;
                    var seats = seatsRepository.findByScreenId(screen.getId()).stream()
                            .map(seat -> new ScreenSeatResponse.SeatOption(seat.getId(), seat.getSeatNumber()))
                            .sorted(Comparator.comparing(ScreenSeatResponse.SeatOption::seatNumber))
                            .toList();
                    return new ScreenSeatResponse(
                            screen.getId(),
                            screen.getName(),
                            theatre != null ? theatre.getName() : null,
                            region != null ? region.getName() : null,
                            screen.getScreenType() != null ? screen.getScreenType().name() : "TWO_D",
                            seats
                    );
                })
                .toList();
    }

    private Set<UUID> bookedSeatIds(UUID screenId, LocalDateTime showTime) {
        return bookingRepository.findByScreenIdAndShowTimeAndBookingStatusIn(screenId, showTime,
                        EnumSet.of(BookingStatus.PENDING, BookingStatus.CONFIRMED))
                .stream()
                .flatMap(booking -> booking.getSeats().stream())
                .map(Seats::getId)
                .collect(Collectors.toSet());
    }
}
