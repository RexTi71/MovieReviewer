package com.dominik.backend.Controllore;

import com.dominik.backend.Fibre.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/register") //пока без верификации email
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        accountService.registerAccount(username,email,password);
        return ResponseEntity.ok("Zarejestrowano użyszkodnika!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password){
        boolean isAuthenticated = accountService.authenticate(username,password);
        if (isAuthenticated){
            return ResponseEntity.ok("Zalogowano pomyślnie!");
        }else {
            return ResponseEntity.status(401).body("Błędny username lub hasło.");
        }
    }
    //POST /api/account/login

    //POST /api/account/register: Create a new user, still requires email verification.

    //GET /api/account/verify?token=...: Verify user email
    //to-do

    //GET /api/account/me: Retrieve all users.

    //GET /api/account/{id}: Retrieve a user by ID.

    //PUT /api/account/{id}: Update an existing user.
    //to-do

    //DELETE /api/account/{id}: Delete a user by ID. (administrator only)
    //to-do
}
