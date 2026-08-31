package com.example.firstspringproj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBookingsForRequestedDate() throws Exception {
        LocalDate date = LocalDate.now().plusDays(1);

        mockMvc.perform(get("/api/bookings")
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].showTime").exists());
    }
}
