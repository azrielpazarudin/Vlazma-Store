package com.vlazma.Utils;

import org.springframework.context.annotation.Configuration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

@Configuration
public class PasswordEncryptor {
    public String encryption(String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("uTSqb9grs1+vUv3iN8lItC0kl65lMG+8");
        return encryptor.encrypt(password);
    }

    public String decryption(String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("uTSqb9grs1+vUv3iN8lItC0kl65lMG+8");
        return encryptor.decrypt(password);
    }
}
