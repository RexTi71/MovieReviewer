package com.dominik.backend.Fibre;

import com.dominik.backend.Archivio.ArchAccount;
import com.dominik.backend.Entità.Account;
import com.dominik.backend.Entità.UserType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<String> registerAccount(String username, String email, String password){
        if(archAccount.existsAccountByUsername(username))
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("użytkownik już istnieje");

        if(archAccount.existsAccountByEmail(email))
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("email już w użyciu");


        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setUserType(UserType.UNVERIFIED);

        //хеширование пароля
        String hashedPassword = passwordEncoder.encode(password);
        account.setPasswordHash(hashedPassword);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("konto utworzone");
    }
    public ResponseEntity<String> verifyEmail(){

    }

    public ResponseEntity<String> login(String username, String rawPassword) {
        Account account = archAccount.findByUsername(username);
        if (account == null) return false;//narazie nie działa ide spać
        //nie dodtyjać bo zabiej

        //Сравнить введенный пароль с сохраненным хешем Bcrypt
        return passwordEncoder.matches(rawPassword, account.getPasswordHash());
    }
}
