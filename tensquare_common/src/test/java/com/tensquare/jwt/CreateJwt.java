package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author 要子康
 * @description CreateJwt
 * @since 2020/1/15 22:00
 */
public class CreateJwt {
    public static void main(String[] args) {

        Date date = new Date();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("小马")
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 60000))
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .claim("role","admin");

        System.out.println(jwtBuilder.compact());

    }
}
