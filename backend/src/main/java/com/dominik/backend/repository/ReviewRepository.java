package com.dominik.backend.repository;

import com.dominik.backend.model.Review;
import com.dominik.backend.model.chiaveComplessa.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
}
