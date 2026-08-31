package com.example.firstspringproj.controller;

import com.example.firstspringproj.models.Coupon;
import com.example.firstspringproj.models.FoodItem;
import com.example.firstspringproj.models.Movie;
import com.example.firstspringproj.models.Region;
import com.example.firstspringproj.models.Show;
import com.example.firstspringproj.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    private final BookingService bookingService;

    public HomeController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Region> regions = bookingService.getAllRegions();
        model.addAttribute("regions", regions);
        model.addAttribute("movies", bookingService.getAllMovies());
        return "home";
    }

    @GetMapping("/movies")
    public String movies(@RequestParam(required = false) String city, Model model) {
        List<Region> regions = bookingService.getAllRegions();
        List<Movie> movies = bookingService.getAllMovies();
        model.addAttribute("regions", regions);
        model.addAttribute("movies", movies);
        model.addAttribute("selectedCity", city);
        return "movies";
    }

    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable UUID id, Model model) {
        Movie movie = bookingService.getMovieById(id).orElseThrow();
        List<Region> regions = bookingService.getAllRegions();
        model.addAttribute("movie", movie);
        model.addAttribute("regions", regions);
        return "movie-details";
    }

    @GetMapping("/booking")
    public String booking(@RequestParam UUID movieId, @RequestParam UUID regionId, Model model) {
        Movie movie = bookingService.getMovieById(movieId).orElseThrow();
        List<Show> shows = bookingService.getShowsForMovieAndRegion(movieId, regionId);
        List<FoodItem> foodItems = bookingService.getFoodItems();
        List<Coupon> coupons = bookingService.getCouponByCode("WELCOME").map(List::of).orElse(List.of());
        model.addAttribute("movie", movie);
        model.addAttribute("shows", shows);
        model.addAttribute("foodItems", foodItems);
        model.addAttribute("coupons", coupons);
        return "booking";
    }
}
