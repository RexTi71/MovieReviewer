package com.dominik.backend.Entità.chiaveComplessa;

import com.dominik.backend.Entità.Comment;
import com.dominik.backend.Entità.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportId implements Serializable {
    private Account account;
    private Comment comment;
}
