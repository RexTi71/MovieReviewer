package com.dominik.backend.entità;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "review")
public class Review {
    @EmbeddedId
    private ReviewId reviewId;

    private Integer rating;
    private String content;
}
