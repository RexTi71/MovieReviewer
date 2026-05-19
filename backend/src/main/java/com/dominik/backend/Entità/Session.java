package com.dominik.backend.Entità;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Session {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Account account;

    String browserName;
    String browserVersion;

    Date data;

    public Session(Account account, String browserName, String browserVersion) {
        this.account = account;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        data = new Date();
    }
}
