package com.dominik.backend.repository;

import com.dominik.backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);

    Account findByEmail(String email);

    boolean existsAccountByEmail(String email);

    boolean existsAccountByUsername(String username);
}
