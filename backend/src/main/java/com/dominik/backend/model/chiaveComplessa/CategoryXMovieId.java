package com.dominik.backend.model.chiaveComplessa;

import com.dominik.backend.model.Category;
import com.dominik.backend.model.Movie;
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
