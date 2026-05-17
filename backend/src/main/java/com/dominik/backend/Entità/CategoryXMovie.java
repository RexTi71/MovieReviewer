package com.dominik.backend.Entità;

import com.dominik.backend.Entità.chiaveComplessa.CategoryXMovieId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Setter
@AllArgsConstructor
@IdClass(CategoryXMovieId.class)
public class CategoryXMovie {
    @Id
    @ManyToOne
    Category category;
    @Id
    @ManyToOne
    Movie movie;
}
