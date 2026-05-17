package com.dominik.backend.entità;

import com.dominik.backend.entità.chiaveComplessa.ReportId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(ReportId.class)
//no
public class Report {
    @Id
    @ManyToOne
    private Account user;
    @Id
    @ManyToOne
    private Comment comment;

    private String reason;
}
