package com.example.bilibili.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 使用高强度密钥,每次启动时随机生成
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("aSElhvq2Gmt/EbMZCcoeTPQFCKxGGi8CqW79/tnVQMs=".getBytes(StandardCharsets.UTF_8)); // 硬编码的密钥
    //    private static final long EXPIRATION_DATE = 3600000L; // 过期时间一小时
    private static final long EXPIRATION_DATE = 1000 * 60 * 60 * 24; // 过期时间一分钟

    // 生成token
    public static String generateToken(String subject) {
        Date now = new Date();

        JwtBuilder builder = Jwts.builder()
//                .setId("weTab")
                .setSubject(subject) // 设置用户信息
                .setIssuedAt(now) // 设置当前时间为发行时间
                .setExpiration(new Date(now.getTime() + EXPIRATION_DATE)) // 设置过期时间
                .signWith(SECRET_KEY); // 签名

        return builder.compact();
    }

    // 校验token
    public static boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // 从token中获取用户信息
    public static String getUserInfo(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}