package com.dominik.backend.controllers;

import com.dominik.backend.service.AccountService;
import com.dominik.backend.service.filemanager.FileStorage;
import com.dominik.backend.service.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/api/avatar")
public class AvatarController {
    FileStorage avatarStorage = new FileStorage("avatars/");

    @Autowired
    AccountService accountService;

    //avatar system
    @GetMapping()
    public ResponseEntity<Resource> dajAvatar(@RequestParam String token) {

        Resource file = avatarStorage.loadAsResource(accountService.getAccountFromToken(token).getId());

        if (file == null)
            return ResponseEntity.notFound().build();

//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file);
    }

    @PostMapping()
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   RedirectAttributes redirectAttributes,
                                                   @RequestParam String token) {

        avatarStorage.store(file,accountService.getAccountFromToken(token).getId());
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return ResponseEntity.ok("\"Zaktualizowane\"");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> dajAvatar(@PathVariable Long id) {

        Resource file = avatarStorage.loadAsResource(id);

        if (file == null)
            return ResponseEntity.notFound().build();

//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file);
    }
}
