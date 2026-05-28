package com.dominik.backend.model.chiaveComplessa;

import com.dominik.backend.model.Comment;
import com.dominik.backend.model.Account;
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
