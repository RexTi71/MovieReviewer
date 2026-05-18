package com.dominik.backend.Fibre;

import com.dominik.backend.Archivio.ArchAccount;
import com.dominik.backend.Entità.Account;
import com.dominik.backend.Entità.UserType;
import com.dominik.backend.Fibre.Mail.Emailer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final ArchAccount archAccount;
    private final PasswordEncoder passwordEncoder;
    private final Emailer emailer;

    public AccountService(ArchAccount archAccount, PasswordEncoder passwordEncoder, Emailer emailer){
        this.archAccount = archAccount;
        this.passwordEncoder = passwordEncoder;
        this.emailer = emailer;
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
    public ResponseEntity<String> verifyEmail(String email){
        Account account = archAccount.findByEmail(email);
        if(account == null){
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("Nie znaleziono użyszkodnika");
        }else{
            emailer.wyslijEmail(
                    account.getEmail(),
                    "Weryfikacja email",
                    "Tutaj weryfikacja email"
            );
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Wysłano email z weryfikacją");
        }
    }

    public ResponseEntity<String> login(String username, String rawPassword) {
        Account account = archAccount.findByUsername(username);
        if(account == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body("Błędny login lub hasło");
        }

        //Сравнить введенный пароль с сохраненным хешем Bcrypt
        boolean isPasswordCorrect = passwordEncoder.matches(rawPassword, account.getPasswordHash());

        if(isPasswordCorrect){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("Zalogowano");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body("Błędny login lub hasło");
        }
    }
}
