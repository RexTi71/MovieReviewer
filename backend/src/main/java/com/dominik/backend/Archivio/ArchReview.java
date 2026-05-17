package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Review;
import com.dominik.backend.Entità.chiaveComplessa.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArchReview extends JpaRepository<Review, ReviewId> {
}
