package com.dominik.backend.service;

import com.dominik.backend.dto.CommentDto;
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
        if(comment.getReplyTo() != null){
            lightWeightComment.setParentId(comment.getReplyTo().getId());
        }

        lightWeightComments.add(lightWeightComment);
        });
        return lightWeightComments;
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }
    public String addComent(CommentDto commentDto)throws IllegalArgumentException{
        Comment comment = new Comment();

        try{
            Account account = accountService.getAccountFromToken(commentDto.getToken());
            comment.setAccount(account);
        }catch (RuntimeException ex){
            throw new IllegalArgumentException("Niepoprawny token");
        }
        if(commentDto.getParentId() != null){
            Comment parentComment = commentRepository.findById(commentDto.getParentId()).orElse(null);
            comment.setReplyTo(parentComment);
        }
        comment.setReview(reviewService.findByAccountIdAndMovieId(commentDto.getReviewAccountId(),commentDto.getMovieId()));
        comment.setDate(LocalDate.now());
        comment.setContent(commentDto.getContent());
        commentRepository.save(comment);

        if(commentRepository.findById(comment.getId()).isEmpty()){
            throw new IllegalArgumentException("Nie udało się utworzyć komentarza");
        }

        return "Pomyślnie dodano komentarz";
    }
}
