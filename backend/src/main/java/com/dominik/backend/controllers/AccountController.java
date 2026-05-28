package com.dominik.backend.controllers;

import com.dominik.backend.model.Account;
import com.dominik.backend.service.AccountService;
import com.dominik.backend.service.filemanager.FileStorage;
import com.dominik.backend.service.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Autowired
    JWT jwt;
    private final AccountService accountService;

    FileStorage avatarStorage;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/register") //пока без верификации email
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        return accountService.registerAccount(username,email,password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password){
        return accountService.login(username,password);
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return accountService.verifyEmail(token);
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
    Account getSelf(@RequestParam String token){
        return accountService.getAccountFromToken(token);
    }
}
