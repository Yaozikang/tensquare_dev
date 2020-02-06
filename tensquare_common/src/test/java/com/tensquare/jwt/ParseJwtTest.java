package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @author 要子康
 * @description ParseJwtTest
 * @since 2020/1/15 22:16
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NzkwOTg0NjUsImV4cCI6MTU3OTA5ODUyNSwicm9sZSI6ImFkbWluIn0.WclN0LQ8yc7yala5zVI6jgMiH23u75EZEXMTOgV9ejQ")
                .getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println(claims.get("role"));
    }
}
