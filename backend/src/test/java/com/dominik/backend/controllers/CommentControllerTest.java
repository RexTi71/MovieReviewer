package com.dominik.backend.controllers;

import com.dominik.backend.dto.CommentDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Comment;
import com.dominik.backend.model.Movie;
import com.dominik.backend.model.Review;
import com.dominik.backend.model.UserType;
import com.dominik.backend.repository.AccountRepository;
import com.dominik.backend.repository.CommentRepository;
import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.repository.ReviewRepository;
import com.dominik.backend.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Mockujemy tylko konta, żeby ominąć walidację JWT
    @MockitoBean
    private AccountService accountService;

    private Account reviewAuthor;
    private Account commentAuthor;
    private Movie movie;
    private Review review;
    private Comment rootComment;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        accountRepository.deleteAll();

        // 1. Autor recenzji
        reviewAuthor = new Account();
        reviewAuthor.setUsername("krytyk");
        reviewAuthor.setEmail("krytyk@test.pl");
        reviewAuthor.setPasswordHash("hash1");
        reviewAuthor.setUserType(UserType.USER);
        accountRepository.save(reviewAuthor);

        // 2. Autor komentarzy
        commentAuthor = new Account();
        commentAuthor.setUsername("widz");
        commentAuthor.setEmail("widz@test.pl");
        commentAuthor.setPasswordHash("hash2");
        commentAuthor.setUserType(UserType.USER);
        accountRepository.save(commentAuthor);

        // 3. Film
        movie = new Movie();
        movie.setTitle("Matrix");
        movie.setRating(5.0f);
        movieRepository.save(movie);

        // 4. Recenzja (Złożony klucz: movie + reviewAuthor)
        review = new Review();
        review.setAccount(reviewAuthor);
        review.setMovie(movie);
        review.setTitle("Klasyk");
        review.setContent("Nie trzeba nic dodawać.");
        review.setRating(5);
        reviewRepository.save(review);

        // 5. Główny komentarz w bazie
        rootComment = new Comment();
        rootComment.setAccount(commentAuthor);
        rootComment.setReview(review);
        rootComment.setContent("Zgadzam się w 100%!");
        rootComment.setDate(LocalDate.now());
        commentRepository.save(rootComment);
    }

    @Test
    void shouldReturnCommentsForReviewMappedToDto() throws Exception {
        mockMvc.perform(get("/api/v1/comment")
                        .param("accountId", reviewAuthor.getId().toString())
                        .param("movieId", movie.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Oczekujemy 1 elementu
                .andExpect(jsonPath("$[0].id", is(rootComment.getId().intValue())))
                .andExpect(jsonPath("$[0].username", is("widz")))
                .andExpect(jsonPath("$[0].content", is("Zgadzam się w 100%!")))
                .andExpect(jsonPath("$[0].parentId").doesNotExist()); // Komentarz nie jest odpowiedzią na inny komentarz
    }

    @Test
    void shouldAddRootCommentSuccessfully() throws Exception {
        when(accountService.getAccountFromToken("valid-token")).thenReturn(commentAuthor);

        CommentDto dto = new CommentDto();
        dto.setToken("valid-token");
        dto.setMovieId(movie.getId());
        dto.setReviewAccountId(reviewAuthor.getId());
        dto.setContent("Oto nowy komentarz główny.");

        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Pomyślnie dodano komentarz")));

        // Baza powinna mieć 2 komentarze (z setUp + nowo dodany)
        assertEquals(2, commentRepository.findAll().size());
    }

    @Test
    void shouldAddReplyToExistingComment() throws Exception {
        when(accountService.getAccountFromToken("valid-token")).thenReturn(commentAuthor);

        // Ustawiamy komentarz jako odpowiedź na 'rootComment'
        CommentDto dto = new CommentDto();
        dto.setToken("valid-token");
        dto.setMovieId(movie.getId());
        dto.setReviewAccountId(reviewAuthor.getId());
        dto.setParentId(rootComment.getId());
        dto.setContent("Odpowiadam na pierwszy komentarz.");

        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Weryfikacja bazy danych dla relacji Parent-Child
        List<Comment> comments = commentRepository.findAll();
        assertEquals(2, comments.size());

        // Szukamy nowo dodanego komentarza (odpowiedzi)
        Comment replyComment = comments.stream()
                .filter(c -> c.getContent().equals("Odpowiadam na pierwszy komentarz."))
                .findFirst()
                .orElseThrow();

        // Upewniamy się, że relacja replyTo zapisała się prawidłowo
        assertNotNull(replyComment.getReplyTo());
        assertEquals(rootComment.getId(), replyComment.getReplyTo().getId());
    }

    @Test
    void shouldReturnBadRequestWhenTokenIsInvalid() throws Exception {
        when(accountService.getAccountFromToken("zly-token")).thenThrow(new RuntimeException("Nie poprawny token"));

        CommentDto dto = new CommentDto();
        dto.setToken("zly-token");
        dto.setMovieId(movie.getId());
        dto.setReviewAccountId(reviewAuthor.getId());
        dto.setContent("To nie powinno przejść.");

        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Niepoprawny token"))); // Obsługa IllegalArgumentException
    }
}