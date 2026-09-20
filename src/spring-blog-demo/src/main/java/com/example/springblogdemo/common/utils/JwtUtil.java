package com.example.springblogdemo.common.utils;

import com.example.springblogdemo.common.constants.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT 生成与解析工具
 *
 * <p>改用 Spring 组件形式，这样密钥可以通过 {@code @Value} 从 application.yml 注入，
 * 不必再硬编码在源码里。使用方请注入 {@code JwtUtil} 而不是静态调用。</p>
 */
@Slf4j
@Component
public class JwtUtil {
    public static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;
    private final Key key;
    public JwtUtil(@Value("${jwt.secret}") String secretString) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    }

    /**
     * 生成 token
     *
     * @param claim 自定义载荷，通常放 id 和 username
     * @return 签名后的 JWT 字符串
     */
    public String genJwt(Map<String, Object> claim) {
        return Jwts.builder()
                .setClaims(claim)                 // 自定义内容(设置载荷)
                .setIssuedAt(new Date())          // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * 解析 token
     *
     * <p>解析失败（签名不对、已过期、格式错误）统一返回 null，由调用方决定怎么处理。</p>
     *
     * @param token 待解析的 JWT
     * @return 解析成功返回 Claims，失败返回 null
     */
    public Claims parseJwt(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            return parser.parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // 注意：这里只记录失败原因，不要把完整 token 打进日志，避免 token 泄露
            log.warn("token 解析失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 从 token 中取出用户 id
     *
     * @param token JWT 字符串
     * @return 用户 id，解析失败返回 null
     */
    public Integer getUserId(String token) {
        Claims claims = parseJwt(token);
        if (claims == null) {
            return null;
        }
        Object id = claims.get(Constant.JWT_CLAIM_ID);
        return id == null ? null : Integer.valueOf(id.toString());
    }
}
