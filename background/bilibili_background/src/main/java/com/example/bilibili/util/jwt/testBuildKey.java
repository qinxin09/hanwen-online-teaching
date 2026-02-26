package com.example.bilibili.util.jwt;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

public class testBuildKey {

    public static void main(String[] args) {
        SecretKey key = generateSecretKey();
        byte[] keyBytes = key.getEncoded();
        String base64Encoded = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Generated secret key: " + base64Encoded);
    }

    public static SecretKey generateSecretKey() {
        byte[] keyBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}