package com.dominik.backend.service;

import com.dominik.backend.dto.ReviewDto;
import com.dominik.backend.dto.ReviewResponseDto;
import com.dominik.backend.model.Review;
import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReviewService
{
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final HomeService homeService;
    private final AccountService accountService;

    public List<ReviewResponseDto> getReviewsForMovie(Long id){
        List<ReviewResponseDto> mappedReviews = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByMovieId(id);
        reviews.forEach(review -> {
            ReviewResponseDto newReview = new ReviewResponseDto();

            newReview.setMovieId(review.getMovie().getId());
            newReview.setTitle(review.getTitle());
            newReview.setRating(review.getRating());
            newReview.setContent(review.getContent());
            newReview.setUserId(review.getAccount().getId());
            newReview.setUsername(review.getAccount().getUsername());

            mappedReviews.add(newReview);
        });
        return mappedReviews;
    }
    public void addReview(ReviewDto reviewDto){
        //Mapowanie DTO do obiektu Review
        //TODO: Sprawdz token przed oddaniem recenzji
        //reviewDto.getToken()
        Review newReview = new Review();
        newReview.setTitle(reviewDto.getTitle());
        newReview.setContent(reviewDto.getContent());
        newReview.setRating(reviewDto.getRating());
        newReview.setAccount(accountService.getAccountFromToken(reviewDto.getToken()));
        newReview.setMovie(homeService.getMovie(reviewDto.getMovieId()));

        reviewRepository.save(newReview);
        updateRating(newReview.getMovie().getId());
    }
    private void updateRating(Long movieId){
        Float averageRating = reviewRepository.avgRating(movieId);
        movieRepository.updateMovieRating(averageRating, movieId);
    }
}
