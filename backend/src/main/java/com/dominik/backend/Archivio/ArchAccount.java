package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchAccount extends JpaRepository<Account,Long> {
}
