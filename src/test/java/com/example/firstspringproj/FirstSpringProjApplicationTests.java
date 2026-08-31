package com.example.firstspringproj;

import com.example.firstspringproj.repository.ScreenRepository;
import com.example.firstspringproj.repository.SeatsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FirstSpringProjApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatsRepository seatsRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateBookingViaApi() throws Exception {
        var screen = screenRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No screen found for booking test"));
        var seat = seatsRepository.findAll().stream()
                .filter(s -> s.getScreen().getId().equals(screen.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No seat found for booking test"));

        var payload = Map.of(
                "customerName", "Test User",
                "customerMobile", "9876543210",
                "movieTitle", "Dune: Part Two",
                "screenId", screen.getId().toString(),
                "showTime", "2099-12-31T19:30:00",
                "seatIds", new String[]{seat.getId().toString()}
        );

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieTitle").value("Dune: Part Two"))
                .andExpect(jsonPath("$.customerName").value("Test User"));
    }
}
