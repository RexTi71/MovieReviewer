package com.dominik.backend.entità;

import com.dominik.backend.entità.chiaveComplessa.ReviewId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "review")
public class Review {
    @Id
    @ManyToOne
    Movie movie;
    @Id
    @ManyToOne
    User user;

    private Integer rating;
    private String content;
}
