package com.dominik.backend.entità.chiaveComplessa;

import com.dominik.backend.entità.Category;
import com.dominik.backend.entità.Movie;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryXMovieId implements Serializable {
    private Category category;
    private Movie movie;
}
