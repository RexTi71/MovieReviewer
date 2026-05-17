package com.dominik.backend.RivistaDiCopertine.JWT;

import com.dominik.backend.RivistaDiCopertine.GestioneDeiFile.StorageException;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@Getter
public class HushHush {//ten serwis dostarcza sekretny sekret dla jwt
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
            System.out.println("Zapisano sekret: "+ Base64.getEncoder().encodeToString(dane));
            Stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Uwaga, nie udało się zapisać sekretu");
        }
    }
    public byte[] wczytajSekret(){
        try {
            InputStream inputStream = new FileInputStream(secretPath);

            byte[] dane = inputStream.readAllBytes();
            System.out.println("Wczytano sekret: "+ Base64.getEncoder().encodeToString(dane));

            inputStream.close();


            return dane;
        } catch (IOException e) {
            return null;
        }

    }

    public static byte[] generujNowySekret() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[64];//512 bitowy sekret
        random.nextBytes(bytes);

        System.out.println("Wygenerowano nowy sekret: "+ Base64.getEncoder().encodeToString(bytes));

        return bytes;
    }
}
