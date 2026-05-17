package com.dominik.backend.Entità.chiaveComplessa;

import com.dominik.backend.Entità.Movie;
import com.dominik.backend.Entità.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewId implements Serializable {
    private Movie movie;
    private Account account;

}
