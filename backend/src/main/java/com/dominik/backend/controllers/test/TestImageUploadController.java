package com.dominik.backend.controllers.test;

import com.dominik.backend.service.filemanager.FileStorage;
import com.dominik.backend.service.jwt.HushHush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//koledzy w ten sposób używamy klasy do przechowywania zdjęć
@Controller
@RequestMapping("/api/test/img")
public class TestImageUploadController {
    @Autowired
    HushHush hushHush;

    FileStorage testowePliki = new FileStorage("imgtest/");

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) {

        Resource file = testowePliki.loadAsResource(id);

        if (file == null)
            return ResponseEntity.notFound().build();

//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file);
    }
    @GetMapping("/next")
    public ResponseEntity nextId(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(testowePliki.nextFreeId());
    }
    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam Long id) {

        testowePliki.store(file,id);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/api/testimg/"+id;
    }
}
