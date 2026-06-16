package com.dominik.backend.controllers;

import com.dominik.backend.dto.ReviewDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Movie;
import com.dominik.backend.model.Review;
import com.dominik.backend.model.UserType;
import com.dominik.backend.repository.AccountRepository;
import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.repository.ReviewRepository;
import com.dominik.backend.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private MovieRepository movieRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @MockitoBean
//    private AccountService accountService;
//
//    private Movie movie;
//    private Account account;
//    private Account account2;
//
//    @BeforeEach
//    void setUp() {
//        reviewRepository.deleteAll();
//        accountRepository.deleteAll();
//        movieRepository.deleteAll();
//
//        //film do testow
//        movie = new Movie();
//        movie.setTitle("Interstellar");
//        movie.setRating(0f);
//        movieRepository.save(movie);
//
//        //pierwszy uzytkownik
//        account = new Account();
//        account.setUsername("kino_maniak");
//        account.setEmail("kino@maniak.pl");
//        account.setPasswordHash("hashed_pass");
//        account.setUserType(UserType.USER);
//        accountRepository.save(account);
//
//        //drugi uzytkownik
//        account2 = new Account();
//        account2.setUsername("drugi_uzytkownik");
//        account2.setEmail("drugi@maniak.pl");
//        account2.setPasswordHash("hashed_pass_2");
//        account2.setUserType(UserType.USER);
//        accountRepository.save(account2);
//
//        //recenzja uzytkownika1
//        Review review = new Review();
//        review.setMovie(movie);
//        review.setAccount(account);
//        review.setRating(1);
//        review.setTitle("Niesamowite gówno.");
//        review.setContent("Nie do obrony.");
//        reviewRepository.save(review);
//    }
//
//    @Test
//    void shouldReturnReviewsForMovie() throws Exception {
//        mockMvc.perform(get("/api/v1/reviews/{id}", movie.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1))) // Oczekujemy 1 recenzji
//                .andExpect(jsonPath("$[0].title", is("Niesamowite gówno.")))
//                .andExpect(jsonPath("$[0].username", is("kino_maniak")))
//                .andExpect(jsonPath("$[0].rating", is(1)));
//    }
//
//    @Test
//    void shouldAddReviewAndRecalculateMovieRating() throws Exception {
//        when(accountService.getAccountFromToken("testowy-token")).thenReturn(account2);
//
//        //recenzja drugiego uzytkownika
//        ReviewDto dto = new ReviewDto();
//        dto.setToken("testowy-token");
//        dto.setMovieId(movie.getId());
//        dto.setRating(5);
//        dto.setTitle("Arcydzieło");
//        dto.setContent("Jeden z najlepszych filmów sci-fi.");
//
//        mockMvc.perform(post("/api/v1/review")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.response", is("Pomyślnie dodano recenzje")));
//
//        List<Review> reviewsInDb = reviewRepository.findAllByMovieId(movie.getId());
//        assertEquals(2, reviewsInDb.size());
//
//        Movie updatedMovie = movieRepository.findById(movie.getId()).orElseThrow();
//        assertEquals(3f, updatedMovie.getRating());
//    }
//
//    @Test
//    void shouldReturnBadRequestWhenTokenIsInvalid() throws Exception {
//        when(accountService.getAccountFromToken("zly-token")).thenThrow(new RuntimeException("Nie poprawny token"));
//
//        ReviewDto dto = new ReviewDto();
//        dto.setToken("zly-token");
//        dto.setMovieId(movie.getId());
//        dto.setRating(5);
//        dto.setTitle("Słabo");
//
//        //oczekiwany status 400 oraz wiadomosc
//        mockMvc.perform(post("/api/v1/review")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.response", is("Nie poprawny token")));
//    }
}