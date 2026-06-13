package com.dominik.backend.dto;

import com.dominik.backend.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResponseDto {
    private Account account;
    private CommentResponseDto comment;
}
