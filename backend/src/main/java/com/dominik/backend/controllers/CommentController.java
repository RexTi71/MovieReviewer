package com.dominik.backend.controllers;

import com.dominik.backend.dto.CommentDto;
import com.dominik.backend.dto.CommentResponseDto;
import com.dominik.backend.service.CommentService;
import com.dominik.backend.service.ResponseBuilder;
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
    private final ResponseBuilder responseBuilder;

    @GetMapping("/comment")
    public ResponseEntity<List<CommentResponseDto>> getCommentsForReview(@RequestParam Long accountId,
                                                                         @RequestParam Long movieId){
        return ResponseEntity.ok(commentService.getCommentsForReview(accountId, movieId));
    }
    @PostMapping("/comment")
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto){
        try {
            return responseBuilder.buildSuccessResponse(commentService.addComent(commentDto));
        }catch (IllegalArgumentException ex){
            return responseBuilder.buildErrorResponse(ex.getMessage());
        }
    }
}
