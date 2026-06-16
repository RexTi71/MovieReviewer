package com.dominik.backend.controllers;

import com.dominik.backend.dto.AccountDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.UserType;
import com.dominik.backend.service.AccountService;
import com.dominik.backend.service.ResponseBuilder;
import com.dominik.backend.service.filemanager.FileStorage;
import com.dominik.backend.service.jwt.JWT;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    private final AccountService accountService;
    FileStorage avatarStorage;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountDto accountDto){
        return accountService.registerAccount(accountDto.getUsername(), accountDto.getEmail(), accountDto.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountDto accountDto){
        return accountService.login(accountDto.getUsername(), accountDto.getPassword());
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token,
                              HttpServletResponse response) {
        ResponseEntity<String> resp = accountService.verifyEmail(token);
        try{
            if(resp.getStatusCode()== HttpStatusCode.valueOf(200))
                //TODO:SUCCES w url
                response.sendRedirect("http://localhost:4200/login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resp;
    }

    @PostMapping("/logout")
    ResponseEntity<String> logout(@RequestParam String token){
        return accountService.logout(token);
    }
    @PostMapping("/delete-session")
    ResponseEntity<String> usunSesje(@RequestParam String token,@RequestParam Long id){
        return accountService.deleteSession(id,accountService.getAccountFromToken(token));
    }

    @GetMapping("/me")
    ResponseEntity<?> getSelf(@RequestParam String token){
        try{
           return ResponseEntity.ok(accountService.getAccountFromToken(token));
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"response\": \"Token wygasł\"}");
        }
    }
    @GetMapping("/admin")
    public ResponseEntity<Boolean> verifyAdmin(@RequestParam String token){
        Boolean isAdmin = accountService.getAccountFromToken(token).getUserType().equals(UserType.ADMIN);

        return ResponseEntity.ok(isAdmin);
    }
}
