package com.dominik.backend.service.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Properties;

@Service
public class Emailer {
    String email = "moviereviewer@interia.pl";
    String scrt = new String(Base64.getDecoder().decode("IWhuOUJSeEZ4fVNeM0xq"));
    //to nie jest wygenerowane przez AI, poprostu nie chciało mi się robić proper way sekret handling
    //proszę nie dotykać
    //please don't touch
    //dotyklem
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    Emailer(){
        mailSender.setHost("poczta.interia.pl");
        mailSender.setPort(587);

        mailSender.setUsername(email);
        mailSender.setPassword(scrt);

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    public void wyslijEmail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
