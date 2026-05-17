package com.dominik.backend.Controllore;

import com.dominik.backend.RivistaDiCopertine.FileStorage;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//koledzy w ten sposób używamy klasy do przechowywania zdjęć
@Controller
@RequestMapping("/api/testimg")
public class TestImageUploadController {
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
