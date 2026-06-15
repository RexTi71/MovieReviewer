package com.dominik.backend.controllers;

import com.dominik.backend.model.Movie;
import com.dominik.backend.repository.CommentRepository;
import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        reviewRepository.deleteAll();
        movieRepository.deleteAll();

        movie1 = new Movie();
        movie1.setTitle("Matrix");
        movie1.setDescription("Hacker learns the truth about reality.");
        movie1.setRating(4.5f);
        movie1.setProductionDate(LocalDate.of(1999, 3, 31));

        movie2 = new Movie();
        movie2.setTitle("Inception");
        movie2.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology.");
        movie2.setRating(2.5f);
        movie2.setProductionDate(LocalDate.of(2010, 7, 16));

        movieRepository.save(movie1);
        movieRepository.save(movie2);
    }

    @Test
    void shouldReturnAllMovies() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Matrix")))
                .andExpect(jsonPath("$[1].title", is("Inception")));
    }

    @Test
    void shouldReturnMovieById() throws Exception {
        Long idToFind = movie1.getId();

        mockMvc.perform(get("/api/v1/movie/{id}", idToFind)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Matrix")))
                .andExpect(jsonPath("$.rating", is(4.5)));
    }

    @Test
    void shouldSearchMovieByTitle() throws Exception {
        mockMvc.perform(get("/api/v1/search/{query}", "cept")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Inception")));
    }

    @Test
    void shouldReturnTop10MoviesSortedByRating() throws Exception {
        Movie movie3 = new Movie();
        movie3.setTitle("The Godfather");
        movie3.setRating(4.9f);
        movieRepository.save(movie3);

        mockMvc.perform(get("/api/v1/top10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is("The Godfather")))
                .andExpect(jsonPath("$[1].title", is("Matrix")))
                .andExpect(jsonPath("$[2].title", is("Inception")));
    }
}