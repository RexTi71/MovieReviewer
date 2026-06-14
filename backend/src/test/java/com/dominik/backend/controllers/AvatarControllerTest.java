package com.dominik.backend.controllers;

import com.dominik.backend.model.Account;
import com.dominik.backend.service.AccountService;
import com.dominik.backend.service.filemanager.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AvatarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AvatarController avatarController;

    @MockitoBean
    private AccountService accountService;

    private FileStorage mockAvatarStorage;

    private Account fakeAccount;

    @BeforeEach
    void setUp() {
        // Tworzymy ręcznie atrapę FileStorage
        mockAvatarStorage = Mockito.mock(FileStorage.class);

        // Wstrzykujemy ją na siłę do kontrolera w miejsce "new FileStorage(...)"
        ReflectionTestUtils.setField(avatarController, "avatarStorage", mockAvatarStorage);

        // Przygotowujemy sztuczne konto dla AccountService
        fakeAccount = new Account();
        fakeAccount.setId(5L);
        fakeAccount.setUsername("Janusz");
    }

    @Test
    void shouldReturnAvatarWhenProvidedWithValidToken() throws Exception {
        // Uczymy serwis kont, by zwracał Janusza po podaniu tokenu
        when(accountService.getAccountFromToken("dobry-token")).thenReturn(fakeAccount);

        // Uczymy atrapę dysku, by zwracała konkretny plik dla ID Janusza (5L)
        byte[] fakeImageBytes = "dane-obrazka".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fakeImageBytes);
        when(mockAvatarStorage.loadAsResource(5L)).thenReturn(resource);

        mockMvc.perform(get("/api/avatar")
                        .param("token", "dobry-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(fakeImageBytes));
    }

    @Test
    void shouldReturnNotFoundWhenAvatarDoesNotExistForToken() throws Exception {
        when(accountService.getAccountFromToken("dobry-token")).thenReturn(fakeAccount);

        // Zwracamy null, co w kontrolerze skutkuje ResponseEntity.notFound()
        when(mockAvatarStorage.loadAsResource(5L)).thenReturn(null);

        mockMvc.perform(get("/api/avatar")
                        .param("token", "dobry-token"))
                .andExpect(status().isNotFound()); // Oczekujemy kodu 404
    }

    @Test
    void shouldReturnAvatarById() throws Exception {
        byte[] fakeImageBytes = "inny-obrazek".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fakeImageBytes);
        when(mockAvatarStorage.loadAsResource(10L)).thenReturn(resource);

        mockMvc.perform(get("/api/avatar/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(fakeImageBytes));
    }

    @Test
    void shouldUploadAvatarSuccessfully() throws Exception {
        when(accountService.getAccountFromToken("dobry-token")).thenReturn(fakeAccount);

        // Tworzymy sztuczny plik zgodny z klasą MultipartFile (symulacja uploadu)
        MockMultipartFile file = new MockMultipartFile(
                "file", // Nazwa zgodna z @RequestParam("file")
                "moj_avatar.png",
                MediaType.IMAGE_PNG_VALUE,
                "dane-obrazka".getBytes()
        );

        // Używamy zapytania multipart() zamiast post(), by móc wysłać plik
        mockMvc.perform(multipart("/api/avatar")
                        .file(file)
                        .param("token", "dobry-token"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"Zaktualizowane\""));

        // Weryfikujemy, czy kontroler faktycznie przekazał plik i ID(5L) do metody store()
        verify(mockAvatarStorage, times(1)).store(file, 5L);
    }
}