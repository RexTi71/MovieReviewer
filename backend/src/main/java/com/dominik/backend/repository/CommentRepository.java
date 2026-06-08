package com.dominik.backend.repository;

import com.dominik.backend.model.Comment;
import com.dominik.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReview(Review review);
}
