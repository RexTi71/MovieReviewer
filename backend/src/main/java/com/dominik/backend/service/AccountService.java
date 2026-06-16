package com.dominik.backend.service;

import com.dominik.backend.repository.AccountRepository;
import com.dominik.backend.repository.SessionRepository;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Session;
import com.dominik.backend.model.UserType;
import com.dominik.backend.service.jwt.JWT;
import com.dominik.backend.service.mail.Emailer;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    JWT jwt;
    @Autowired
    SessionRepository sessionRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final Emailer emailer;

    public ResponseEntity<String> badRequest(String reason){
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("\""+reason+"\"");
    }

    public ResponseEntity<String> sukces(String message){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("\""+message+"\"");
    }

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, Emailer emailer){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailer = emailer;
    }


    public ResponseEntity<String> registerAccount(String username, String email, String password){
        if(accountRepository.existsAccountByUsername(username))
            return badRequest("użytkownik już istnieje");

        if(accountRepository.existsAccountByEmail(email))
            return badRequest("email już w użyciu");


        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setUserType(UserType.UNVERIFIED);

        //хеширование пароля
        String hashedPassword = passwordEncoder.encode(password);
        account.setPasswordHash(hashedPassword);
        accountRepository.save(account);

        sendVerificationEmail(email);
        return sukces("Konto utworzone, sprawdź poczte i zweryfikuj adress email przed zalogowaniem.");
    }

    public void sendVerificationEmail(String email){
        Map<String,String> map = HashMap.newHashMap(1);
        map.put("email",email);
        String token = jwt.buildToken(map,"email-verification");

        emailer.wyslijEmail(email,"Weryfikacja adresu email","zweryfikuj swój adres email klikając w ten link : http://localhost:8080/api/auth/verify?token="+token);
    }

    public ResponseEntity<String> verifyEmail(String token){
        if(jwt.isTokenExpired(token))
            return badRequest("\"Nie aktualny token\"");

        Claims claims = jwt.extractAllClaims(token);

        String email = claims.get("email",String.class);

        if(email==null)
            return badRequest("\"Nie poprawny token\"");

        System.out.println(email);

        Account account = accountRepository.findByEmail(email);
        if(account==null)
            return badRequest("Niepoprawny email");

        account.setUserType(UserType.USER);

        accountRepository.save(account);

        return sukces("Adres "+email+" zweryfikowany możesz się zalogować");

    }

    public Session getSessionFromToken(String token){
        Claims claims = jwt.extractAllClaims(token);

        String sesja = claims.get("sesja",String.class);

        if(sesja==null)
            throw new RuntimeException("\"Nie poprawny token\"");

        return  sessionRepository.findById(Long.parseLong(sesja)).orElseThrow();
    }

    public Account getAccountFromToken(String token){
//        if(jwt.isTokenExpired(token))
//            return badRequest("\"Nie aktualny token\""); wywalone


        return  getSessionFromToken(token).getAccount();
    }
    public Account getAccountFromUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public ResponseEntity<String> logout(String token){
        sessionRepository.delete(getSessionFromToken(token));
        return sukces("Wylogowano");
    }
    public ResponseEntity<String> deleteSession(Long id,Account account){
        Session session = sessionRepository.findById(id).orElseThrow();

        if (Objects.equals(session.getAccount().getId(), account.getId()))
            return sukces("wylogowano");
        return badRequest("To nie twoja sesja");
    }

    public ResponseEntity<String> login(String username, String rawPassword) {
        Account account = accountRepository.findByUsername(username);
        if(account == null){
            return badRequest("Błędny login lub hasło");
        }

        //Сравнить введенный пароль с сохраненным хешем Bcrypt
        boolean isPasswordCorrect = passwordEncoder.matches(rawPassword, account.getPasswordHash());

        if (account.getUserType()==UserType.UNVERIFIED){
            sendVerificationEmail(account.getEmail());
            return badRequest("Proszę zweryfikować adres email przed zalogowaniem");
        }


        if(!isPasswordCorrect)
            return badRequest("Błędny login lub hasło");

        Session sesja = new Session(account,"unknown","unknown");//to-do

        Long id_sesji = sessionRepository.save(sesja).getId();

        Map<String,String> map = HashMap.newHashMap(1);
        map.put("sesja",id_sesji.toString());
        String token = jwt.buildToken(map,"sesja-użytkownika");

        return sukces(token);
    }
}
