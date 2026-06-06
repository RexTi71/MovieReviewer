package com.dominik.backend.service;

import com.dominik.backend.model.Account;
import com.dominik.backend.model.Comment;
import com.dominik.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final ReviewService reviewService;

    public List<Comment> getCommentsForReview(Long id){
        return commentRepository.findAllByAccountId(id);
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
