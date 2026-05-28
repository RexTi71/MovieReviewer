package com.dominik.backend.service.jwt;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@Getter
public class HushHush {//ten serwis dostarcza sekretny sekret dla jwt
    Logger logger = LoggerFactory.getLogger(HushHush.class);
    String secretPath = "secret.key";

    byte[] sekret;

    HushHush(){
        sekret = wczytajSekret();
        if (sekret!=null)
            return;

        sekret = generujNowySekret();

        zapiszSekret(sekret);
    }
    public void zapiszSekret(byte[] dane){
        try {
            OutputStream Stream = new FileOutputStream(secretPath);
            Stream.write(dane);
            logger.info("Zapisano sekret: "+ Base64.getEncoder().encodeToString(dane));
            Stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Uwaga, nie udało się zapisać sekretu");
        }
    }
    public byte[] wczytajSekret(){
        try {
            InputStream inputStream = new FileInputStream(secretPath);

            byte[] dane = inputStream.readAllBytes();
            logger.info("Wczytano sekret: "+ Base64.getEncoder().encodeToString(dane));

            inputStream.close();


            return dane;
        } catch (IOException e) {
            return null;
        }

    }

    public byte[] generujNowySekret() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[64];//512 bitowy sekret
        random.nextBytes(bytes);

        logger.info("Wygenerowano nowy sekret: "+ Base64.getEncoder().encodeToString(bytes));

        return bytes;
    }
}
