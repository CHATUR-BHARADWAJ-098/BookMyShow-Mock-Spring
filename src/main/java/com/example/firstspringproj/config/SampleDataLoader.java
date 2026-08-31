package com.example.firstspringproj.config;

import com.example.firstspringproj.repository.BookingRepository;
import com.example.firstspringproj.repository.RegionRepository;
import com.example.firstspringproj.repository.ScreenRepository;
import com.example.firstspringproj.repository.SeatsRepository;
import com.example.firstspringproj.repository.TheatreRepository;
import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.models.BookingStatus;
import com.example.firstspringproj.models.Region;
import com.example.firstspringproj.models.Screen;
import com.example.firstspringproj.models.ScreenType;
import com.example.firstspringproj.models.SeatType;
import com.example.firstspringproj.models.Seats;
import com.example.firstspringproj.models.Theatre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class SampleDataLoader implements CommandLineRunner {
    private final RegionRepository regionRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final SeatsRepository seatsRepository;
    private final BookingRepository bookingRepository;

    public SampleDataLoader(RegionRepository regionRepository, TheatreRepository theatreRepository,
                            ScreenRepository screenRepository, SeatsRepository seatsRepository,
                            BookingRepository bookingRepository) {
        this.regionRepository = regionRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.seatsRepository = seatsRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (regionRepository.count() > 0) {
            return;
        }

        Region region = new Region();
        region.setName("Bengaluru");
        regionRepository.save(region);

        Theatre theatre = new Theatre();
        theatre.setName("PVR Koramangala");
        theatre.setAddress("Forum Mall, Koramangala, Bengaluru");
        theatre.setRegion(region);
        theatreRepository.save(theatre);

        Screen screen = new Screen();
        screen.setName("Audi 1");
        screen.setScreenType(ScreenType.IMAX);
        screen.setTheatre(theatre);
        screenRepository.save(screen);

        Seats seatA1 = seat("A1", SeatType.REGULAR, screen);
        Seats seatA2 = seat("A2", SeatType.REGULAR, screen);
        Seats seatP1 = seat("P1", SeatType.PREMIUM, screen);
        Seats seatR1 = seat("R1", SeatType.RECLINER, screen);
        seatsRepository.saveAll(List.of(seatA1, seatA2, seatP1, seatR1));

        Booking booking = new Booking();
        booking.setCustomerName("Demo User");
        booking.setCustomerEmail("demo@example.com");
        booking.setScreen(screen);
        booking.setShowTime(LocalDateTime.now().plusDays(1).withHour(19).withMinute(30).withSecond(0).withNano(0));
        booking.setSeats(Set.of(seatA1, seatA2));
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }

    private Seats seat(String seatNumber, SeatType seatType, Screen screen) {
        Seats seat = new Seats();
        seat.setSeatNumber(seatNumber);
        seat.setSeatType(seatType);
        seat.setScreen(screen);
        return seat;
    }
}
