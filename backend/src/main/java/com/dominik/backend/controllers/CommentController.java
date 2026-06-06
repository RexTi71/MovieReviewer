package com.dominik.backend.controllers;

import com.dominik.backend.dto.CommentDto;
import com.dominik.backend.model.Comment;
import com.dominik.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CommentController {
    private final CommentService commentService;

    private String buildResponse(String message){
        return "\"" + message + "\"";
    }
    @GetMapping("/comment/{accountId}")
    public ResponseEntity<List<Comment>> getCommentsForReview(@PathVariable Long accountId){
        return ResponseEntity.ok(commentService.getCommentsForReview(accountId));
    }
    @PostMapping("/comment")
    public ResponseEntity<String> addComent(@RequestBody CommentDto commentDto){
        try {
            String response = buildResponse(commentService.addComent(commentDto.getContent(),
                                                                    commentDto.getToken(),
                                                                    commentDto.getMovieId()));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(buildResponse(ex.getMessage()));
        }
    }
}
