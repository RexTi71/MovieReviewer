package com.dominik.backend.Controllore;

import com.dominik.backend.RivistaDiCopertine.Mail.Emailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/testemailer")
public class TestEmailer {
    @Autowired
    Emailer emailer;

    @PostMapping()
    public String handleFileUpload(@RequestParam("email") String email) {
        emailer.wyslijEmail(email,"test","test");
        return "redirect:/testemailer.html";
    }
}
