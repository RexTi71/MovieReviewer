package com.dominik.backend.model;

import com.dominik.backend.model.chiaveComplessa.ReportId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(ReportId.class)
public class Report {
    @Id
    @ManyToOne
    private Account account;
    @Id
    @ManyToOne
    private Comment comment;

}
