package com.dominik.backend.Controllore;

import com.dominik.backend.Fibre.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    private final AccountService accountService;

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
    public String verifyEmail(@RequestParam String jwt){

    }
}
