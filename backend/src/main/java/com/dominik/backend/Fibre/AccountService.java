package com.dominik.backend.Fibre;

import com.dominik.backend.Archivio.ArchAccount;
import com.dominik.backend.Entità.Account;
import com.dominik.backend.Entità.UserType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final ArchAccount archAccount;
    private final PasswordEncoder passwordEncoder;

    public AccountService(ArchAccount archAccount, PasswordEncoder passwordEncoder){
        this.archAccount = archAccount;
        this.passwordEncoder = passwordEncoder;
    }

    public Account registerAccount(String username, String email, String password){
        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setUserType(UserType.USER);

        //хеширование пароля
        String hashedPassword = passwordEncoder.encode(password);
        account.setPasswordHash(hashedPassword);

        return archAccount.save(account);
    }

    public boolean authenticate(String name, String rawPassword) {
        Account account = archAccount.findByUsername(name);
        if (account == null) return false;

        //Сравнить введенный пароль с сохраненным хешем Bcrypt
        return passwordEncoder.matches(rawPassword, account.getPasswordHash());
    }
}
