package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchAccount extends JpaRepository<Account,Long> {
    Account findByUsername(String username);

    boolean findByEmail(String email);

    boolean existsAccountByEmail(String email);

    boolean existsAccountByUsername(String username);
}
