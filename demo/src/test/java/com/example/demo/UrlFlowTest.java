package com.example.demo;

import com.example.demo.Url.URL;
import com.example.demo.Url.UrlRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UrlFlowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UrlRepo urlRepo;

    private Long userId;

    @BeforeEach
    void setup() {
        urlRepo.deleteAll();
        userRepo.deleteAll();
        User user = new User();
        user.setUsername("testuser");
        userId = userRepo.save(user).getUserId();
    }

    @Test
    void shortenThenGetThenRedirectThenDelete() throws Exception {
        // shorten
        String response = mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"https://google.com\", \"userId\": " + userId + "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortenedUrl").isNotEmpty())
                .andExpect(jsonPath("$.url").value("https://google.com"))
                .andReturn().getResponse().getContentAsString();

        String shortCode = com.jayway.jsonpath.JsonPath.read(response, "$.shortenedUrl");
        Long urlId = ((Number) com.jayway.jsonpath.JsonPath.read(response, "$.urlId")).longValue();

        // get all urls for user
        mockMvc.perform(get("/api/urls").param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shortenedUrl").value(shortCode));

        // redirect
        mockMvc.perform(get("/").param("shortUrl", shortCode))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://google.com"));

        // delete
        mockMvc.perform(delete("/api/urls").param("urlId", urlId.toString()))
                .andExpect(status().isNoContent());

        // confirm deleted
        mockMvc.perform(get("/api/urls").param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
