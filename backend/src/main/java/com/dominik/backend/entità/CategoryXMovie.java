package com.dominik.backend.entità;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Setter
@AllArgsConstructor
public class CategoryXMovie {
    @Id
    @ManyToOne
    Category category;
    @Id
    @ManyToOne
    Movie movie;
}
