package com.dominik.backend.controllers;

import com.dominik.backend.dto.ReviewDto;

import com.dominik.backend.dto.ReviewResponseDto;
import com.dominik.backend.model.Review;
import com.dominik.backend.service.ResponseBuilder;
import com.dominik.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewService reviewService;
    private final ResponseBuilder responseBuilder;

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsForMovie(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.getReviewsForMovie(id));
    }
    @PostMapping("/review")
    public ResponseEntity<String> addReview(@RequestBody ReviewDto reviewDto){
        try{
            return responseBuilder.buildSuccessResponse(reviewService.addReview(reviewDto));
        }catch (Exception ex){
            return responseBuilder.buildErrorResponse(ex.getMessage());
        }
    }
}
