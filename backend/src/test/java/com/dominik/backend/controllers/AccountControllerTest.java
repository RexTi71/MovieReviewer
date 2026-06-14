package com.dominik.backend.controllers;

import com.dominik.backend.dto.AccountDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Session;
import com.dominik.backend.model.UserType;
import com.dominik.backend.repository.AccountRepository;
import com.dominik.backend.repository.SessionRepository;
import com.dominik.backend.service.jwt.JWT;
import com.dominik.backend.service.mail.Emailer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWT jwt; // Prawdziwy mechanizm JWT - wygenerujemy nim prawidłowe tokeny do testów

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Zaślepiamy Emailer, żeby nie wysyłać prawdziwych e-maili przy rejestracji
    @MockitoBean
    private Emailer emailer;

    private Account verifiedAccount;
    private Account unverifiedAccount;

    @BeforeEach
    void setUp() {
        sessionRepository.deleteAll();
        accountRepository.deleteAll();

        // Zwykłe, zweryfikowane konto gotowe do logowania
        verifiedAccount = new Account();
        verifiedAccount.setUsername("poprawny_user");
        verifiedAccount.setEmail("poprawny@test.pl");
        verifiedAccount.setPasswordHash(passwordEncoder.encode("tajnehaslo123"));
        verifiedAccount.setUserType(UserType.USER);
        accountRepository.save(verifiedAccount);

        // Konto niezweryfikowane
        unverifiedAccount = new Account();
        unverifiedAccount.setUsername("niezweryfikowany");
        unverifiedAccount.setEmail("niezweryfikowany@test.pl");
        unverifiedAccount.setPasswordHash(passwordEncoder.encode("tajnehaslo123"));
        unverifiedAccount.setUserType(UserType.UNVERIFIED);
        accountRepository.save(unverifiedAccount);
    }

    @Test
    void shouldRegisterNewAccountSuccessfully() throws Exception {
        AccountDto dto = new AccountDto();
        dto.setUsername("nowy_uzytkownik");
        dto.setEmail("nowy@test.pl");
        dto.setPassword("mojeHaslo");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("\"Konto utworzone, sprawdź poczte i zweryfikuj adress email przed zalogowaniem.\""));

        // Sprawdzamy, czy w bazie pojawił się nowy rekord
        assertTrue(accountRepository.existsAccountByUsername("nowy_uzytkownik"));
    }

    @Test
    void shouldFailRegistrationIfUsernameExists() throws Exception {
        AccountDto dto = new AccountDto();
        dto.setUsername("poprawny_user"); // Nazwa zajęta przez verifiedAccount z setUp()
        dto.setEmail("inny@test.pl");
        dto.setPassword("haslo123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"użytkownik już istnieje\""));
    }

    @Test
    void shouldLoginSuccessfullyAndCreateSession() throws Exception {
        AccountDto dto = new AccountDto();
        dto.setUsername("poprawny_user");
        dto.setPassword("tajnehaslo123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isString()); // Oczekujemy tokenu JWT

        // Sprawdzamy, czy po poprawnym logowaniu wygenerowano sesję w bazie danych
        assertEquals(1, sessionRepository.findAll().size());
    }

    @Test
    void shouldBlockLoginIfEmailUnverifiedAndSendEmail() throws Exception {
        AccountDto dto = new AccountDto();
        dto.setUsername("niezweryfikowany");
        dto.setPassword("tajnehaslo123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("\"Proszę zweryfikować adres email przed zalogowaniem\""));

        // Sprawdzamy, czy aplikacja próbowała wysłać e-mail z linkiem do weryfikacji
        verify(emailer, times(1)).wyslijEmail(eq("niezweryfikowany@test.pl"), anyString(), anyString());
    }

    @Test
    void shouldReturnSelfDataWhenProvidedWithValidToken() throws Exception {
        // Ręcznie tworzymy sesję w bazie
        Session session = new Session(verifiedAccount, "unknown", "unknown");
        session = sessionRepository.save(session);

        // Ręcznie generujemy prawdziwy token JWT powiązany z tą sesją
        Map<String, String> claims = new HashMap<>();
        claims.put("sesja", session.getId().toString());
        String token = jwt.buildToken(claims, "sesja-użytkownika");

        // Weryfikujemy endpoint /me
        mockMvc.perform(get("/api/auth/me").param("token", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("poprawny_user")))
                .andExpect(jsonPath("$.email", is("poprawny@test.pl")));
    }

    @Test
    void shouldVerifyEmailAndRedirectToLogin() throws Exception {
        // Generujemy token aktywacyjny dla niezweryfikowanego konta
        Map<String, String> claims = new HashMap<>();
        claims.put("email", "niezweryfikowany@test.pl");
        String token = jwt.buildToken(claims, "email-verification");

        mockMvc.perform(get("/api/auth/verify").param("token", token))
                .andExpect(status().is3xxRedirection()) // Oczekujemy kodu 30x (przekierowanie)
                .andExpect(redirectedUrl("http://localhost:4200/login")); // Sprawdzamy czy url to ten z sendRedirect

        // Sprawdzamy czy w bazie zmienił się status użytkownika na zweryfikowany
        Account updatedAccount = accountRepository.findByEmail("niezweryfikowany@test.pl");
        assertEquals(UserType.USER, updatedAccount.getUserType());
    }
}