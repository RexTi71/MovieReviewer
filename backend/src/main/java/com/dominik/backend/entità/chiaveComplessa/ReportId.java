package com.dominik.backend.entità.chiaveComplessa;

import com.dominik.backend.entità.Comment;
import com.dominik.backend.entità.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportId implements Serializable {
    private Account user;
    private Comment comment;
}
