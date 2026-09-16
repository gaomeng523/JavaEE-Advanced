package com.example.springblogdemo.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {
    public static final long EXPIRATION_TIME = 7*24*60*60*1000;

    public static final String secretString = "+JEq/o2bYDEtaECkxKCeAt0i3yA0IPUlV2rxFVGS+v4=";
    public static final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));

    public static String genJwt(Map<String , Object> claim){
        return Jwts.builder()
                .setClaims(claim) //自定义内容(设置载荷)
                .setIssuedAt(new Date()) //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public static Claims parseJwt(String token) {
        JwtParser builder = Jwts.parserBuilder().setSigningKey(key).build();
        Claims body = null;
        try {
            body = (Claims) builder.parse(token).getBody();
        }catch (Exception e){
            log.error("token 不合法，token:{}",token);
        }
        return body;
    }
}
