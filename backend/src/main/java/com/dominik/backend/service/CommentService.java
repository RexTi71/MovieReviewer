package com.dominik.backend.service;

import com.dominik.backend.dto.CommentResponseDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Comment;
import com.dominik.backend.model.Review;
import com.dominik.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final ReviewService reviewService;

    public List<CommentResponseDto> getCommentsForReview(Long accountId,
                                              Long movieId){
        Review review = reviewService.findByAccountIdAndMovieId(accountId, movieId);
        List<Comment> comments = commentRepository.findByReview(review);
        List<CommentResponseDto> lightWeightComments = new ArrayList<>();
        comments.forEach(comment -> {

        CommentResponseDto lightWeightComment = new CommentResponseDto();
        lightWeightComment.setId(comment.getId());
        lightWeightComment.setUserId(comment.getAccount().getId());
        lightWeightComment.setUsername(comment.getAccount().getUsername());
        lightWeightComment.setContent(comment.getContent());
        lightWeightComment.setDate(comment.getDate());

        lightWeightComments.add(lightWeightComment);
        });
        return lightWeightComments;
    }

    public String addComent(String content,
                            String token,
                            Long movieId)throws IllegalArgumentException{
        Comment comment = new Comment();

        try{
            Account account = accountService.getAccountFromToken(token);
            comment.setAccount(account);
            comment.setReview(reviewService.findByAccountIdAndMovieId(account.getId(),movieId));
        }catch (RuntimeException ex){
            throw new IllegalArgumentException("Niepoprawny token");
        }

        comment.setDate(LocalDate.now());
        comment.setContent(content);
        commentRepository.save(comment);

        if(commentRepository.findById(comment.getId()).isEmpty()){
            throw new IllegalArgumentException("Nie udało się utworzyć komentarza");
        }

        return "Pomyślnie dodano komentarz";
    }
}
