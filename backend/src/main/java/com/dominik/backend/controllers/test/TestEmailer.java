package com.dominik.backend.controllers.test;

import com.dominik.backend.service.mail.Emailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/test/emailer")
public class TestEmailer {
    @Autowired
    Emailer emailer;

    @PostMapping()
    public String handleFileUpload(@RequestParam("email") String email) {
        emailer.wyslijEmail(email,"test","test https://google.pl");
        return "redirect:/testemailer.html";
    }
}
