package com.dominik.backend.entità.chiaveComplessa;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ReviewId implements Serializable {
    private Long userId;
    private Long filmId;
}
