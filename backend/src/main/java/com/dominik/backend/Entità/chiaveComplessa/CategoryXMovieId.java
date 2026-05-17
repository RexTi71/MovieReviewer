package com.dominik.backend.Entità.chiaveComplessa;

import com.dominik.backend.Entità.Category;
import com.dominik.backend.Entità.Movie;
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
