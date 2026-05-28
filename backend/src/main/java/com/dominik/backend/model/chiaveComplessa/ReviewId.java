package com.dominik.backend.model.chiaveComplessa;

import com.dominik.backend.model.Movie;
import com.dominik.backend.model.Account;
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
