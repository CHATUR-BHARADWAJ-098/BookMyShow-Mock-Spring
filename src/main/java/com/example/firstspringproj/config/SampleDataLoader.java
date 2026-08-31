package com.example.firstspringproj.config;

import com.example.firstspringproj.models.Booking;
import com.example.firstspringproj.models.BookingStatus;
import com.example.firstspringproj.models.Region;
import com.example.firstspringproj.models.Screen;
import com.example.firstspringproj.models.ScreenType;
import com.example.firstspringproj.models.SeatType;
import com.example.firstspringproj.models.Seats;
import com.example.firstspringproj.models.Theatre;
import com.example.firstspringproj.repository.BookingRepository;
import com.example.firstspringproj.repository.RegionRepository;
import com.example.firstspringproj.repository.ScreenRepository;
import com.example.firstspringproj.repository.SeatsRepository;
import com.example.firstspringproj.repository.TheatreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        Region bengaluru = createRegion("Bengaluru");
        Region hyderabad = createRegion("Hyderabad");
        Region mumbai = createRegion("Mumbai");

        Theatre pvrKoramangala = createTheatre("PVR Koramangala", "Forum Mall, Bengaluru", bengaluru);
        Theatre inoxPhoenix = createTheatre("INOX Phoenix Marketcity", "Whitefield, Bengaluru", bengaluru);
        Theatre asianGachibowli = createTheatre("Asian Cinemas Gachibowli", "Gachibowli, Hyderabad", hyderabad);
        Theatre cinepolisLowerParel = createTheatre("Cinepolis Lower Parel", "Lower Parel, Mumbai", mumbai);

        Screen screen1 = createScreen("Audi 1", ScreenType.IMAX, pvrKoramangala);
        Screen screen2 = createScreen("Audi 2", ScreenType.DOLBY, pvrKoramangala);
        Screen screen3 = createScreen("Screen 3", ScreenType.LASER, inoxPhoenix);
        Screen screen4 = createScreen("Screen 4", ScreenType.DOLBY, asianGachibowli);
        Screen screen5 = createScreen("Screen 5", ScreenType.IMAX, cinepolisLowerParel);

        List<Seats> createdSeats = new ArrayList<>();
        createdSeats.addAll(createSeatBlock(screen1, "A1", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen1, "A2", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen1, "P1", SeatType.PREMIUM));
        createdSeats.addAll(createSeatBlock(screen1, "R1", SeatType.RECLINER));
        createdSeats.addAll(createSeatBlock(screen2, "B1", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen2, "B2", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen2, "P2", SeatType.PREMIUM));
        createdSeats.addAll(createSeatBlock(screen3, "C1", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen3, "C2", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen3, "R2", SeatType.RECLINER));
        createdSeats.addAll(createSeatBlock(screen4, "D1", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen4, "D2", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen5, "E1", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen5, "E2", SeatType.REGULAR));
        createdSeats.addAll(createSeatBlock(screen5, "P3", SeatType.PREMIUM));
        seatsRepository.saveAll(createdSeats);

        createBooking("Demo User", "9876543210", "Kalki 2898 AD", screen1, LocalDate.now().plusDays(1).atTime(19, 30), Set.of(findSeat(createdSeats, screen1, "A1"), findSeat(createdSeats, screen1, "A2")));
        createBooking("Aditi", "9876543211", "Salaar", screen2, LocalDate.now().plusDays(2).atTime(18, 0), Set.of(findSeat(createdSeats, screen2, "B1"), findSeat(createdSeats, screen2, "P2")));
        createBooking("Ravi", "9876543212", "Pushpa 2", screen3, LocalDate.now().plusDays(3).atTime(21, 15), Set.of(findSeat(createdSeats, screen3, "C1"), findSeat(createdSeats, screen3, "R2")));
        createBooking("Meera", "9876543213", "Dhoom 3", screen4, LocalDate.now().plusDays(1).atTime(16, 45), Set.of(findSeat(createdSeats, screen4, "D1"), findSeat(createdSeats, screen4, "D2")));
        createBooking("Arjun", "9876543214", "War 2", screen5, LocalDate.now().plusDays(4).atTime(20, 30), Set.of(findSeat(createdSeats, screen5, "E1"), findSeat(createdSeats, screen5, "P3")));
        createBooking("Sana", "9876543215", "Interstellar", screen1, LocalDate.now().plusDays(5).atTime(19, 00), Set.of(findSeat(createdSeats, screen1, "R1"), findSeat(createdSeats, screen1, "P1")));
        createBooking("Vikram", "9876543216", "Avatar: Way of Water", screen2, LocalDate.now().plusDays(2).atTime(22, 0), Set.of(findSeat(createdSeats, screen2, "B2"), findSeat(createdSeats, screen2, "P2")));
        createBooking("Nisha", "9876543217", "Mission Impossible", screen3, LocalDate.now().plusDays(6).atTime(17, 30), Set.of(findSeat(createdSeats, screen3, "C2")));
        createBooking("Rahul", "9876543218", "Animal", screen4, LocalDate.now().plusDays(1).atTime(20, 15), Set.of(findSeat(createdSeats, screen4, "D2")));
    }

    private Region createRegion(String name) {
        Region region = new Region();
        region.setName(name);
        return regionRepository.save(region);
    }

    private Theatre createTheatre(String name, String address, Region region) {
        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);
        theatre.setRegion(region);
        return theatreRepository.save(theatre);
    }

    private Screen createScreen(String name, ScreenType screenType, Theatre theatre) {
        Screen screen = new Screen();
        screen.setName(name);
        screen.setScreenType(screenType);
        screen.setTheatre(theatre);
        return screenRepository.save(screen);
    }

    private List<Seats> createSeatBlock(Screen screen, String seatNumber, SeatType seatType) {
        Seats seat = new Seats();
        seat.setSeatNumber(seatNumber);
        seat.setSeatType(seatType);
        seat.setScreen(screen);
        return List.of(seat);
    }

    private Seats findSeat(List<Seats> seats, Screen screen, String seatNumber) {
        return seats.stream()
                .filter(seat -> seat.getScreen().getId().equals(screen.getId()) && seat.getSeatNumber().equals(seatNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Seat not found: " + screen.getName() + " - " + seatNumber));
    }

    private void createBooking(String customerName, String customerMobile, String movieTitle, Screen screen, LocalDateTime showTime, Set<Seats> seats) {
        Booking booking = new Booking();
        booking.setCustomerName(customerName);
        booking.setCustomerMobile(customerMobile);
        booking.setMovieTitle(movieTitle);
        booking.setScreen(screen);
        booking.setShowTime(showTime);
        booking.setSeats(seats);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }
}
