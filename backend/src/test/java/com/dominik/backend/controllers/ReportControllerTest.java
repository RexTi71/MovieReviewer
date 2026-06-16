package com.dominik.backend.controllers;

import com.dominik.backend.dto.ReportDto;
import com.dominik.backend.model.*;
import com.dominik.backend.repository.*;
import com.dominik.backend.service.AccountService;
import com.dominik.backend.service.CommentService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private CommentService commentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Account reporter;
    private Comment comment;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
        commentRepository.deleteAll();
        reviewRepository.deleteAll();
        movieRepository.deleteAll();
        accountRepository.deleteAll();

        reportRepository.flush();

        //konto zgłaszajacego
        reporter = new Account();
        reporter.setUsername("sygnalista");
        reporter.setEmail("sygnalista@test.pl");
        reporter.setPasswordHash("hash");
        reporter.setUserType(UserType.USER);
        accountRepository.save(reporter);

        //film
        Movie movie = new Movie();
        movie.setTitle("Władca Pierścieni");
        movie.setRating(10.0f);
        movieRepository.save(movie);

        //recenzja do filmu
        Review review = new Review();
        review.setAccount(reporter);
        review.setMovie(movie);
        review.setTitle("Super");
        review.setContent("Super film");
        review.setRating(10);
        reviewRepository.save(review);

        //zglaszany komentarz
        comment = new Comment();
        comment.setAccount(reporter);
        comment.setReview(review);
        comment.setContent("Bardzo niekulturalny komentarz");
        comment.setDate(LocalDate.now());
        commentRepository.save(comment);

        //zgloszenie w bazie
        Report report = new Report();
        report.setAccount(reporter);
        report.setComment(comment);
        reportRepository.save(report);
    }

    @Test
    void shouldReturnAllReports() throws Exception {
        mockMvc.perform(get("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].account.username", is("sygnalista")))
                .andExpect(jsonPath("$[0].comment.content", is("Bardzo niekulturalny komentarz")));
    }

    @Test
    void shouldAddReportSuccessfully() throws Exception {
        //czystka tabeli, zeby bylo 0 zgloszen
        reportRepository.deleteAll();
        reportRepository.flush();

        when(accountService.getAccountFromUsername("sygnalista")).thenReturn(reporter);
        when(commentService.getCommentById(comment.getId())).thenReturn(comment);

        ReportDto dto = new ReportDto();
        dto.setUsername("sygnalista");
        dto.setCommentId(comment.getId());

        mockMvc.perform(post("/api/v1/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("Dziękujemy za zgłoszenie")));

        assertEquals(1, reportRepository.findAll().size());
    }

    @Test
    void shouldReturnBadRequestWhenUserNotFound() throws Exception {
        when(accountService.getAccountFromUsername("nieznany_user")).thenReturn(null);

        ReportDto dto = new ReportDto();
        dto.setUsername("nieznany_user");
        dto.setCommentId(comment.getId());

        mockMvc.perform(post("/api/v1/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response", is("Nie ma takiego użytkownika")));
    }

    @Test
    void shouldReturnBadRequestWhenCommentNotFound() throws Exception {
        when(accountService.getAccountFromUsername("sygnalista")).thenReturn(reporter);
        when(commentService.getCommentById(999L)).thenReturn(null);

        ReportDto dto = new ReportDto();
        dto.setUsername("sygnalista");
        dto.setCommentId(999L);

        mockMvc.perform(post("/api/v1/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response", is("Nie ma takiego komentarza")));
    }
}