package com.dominik.backend.Controllore;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountManager {
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
