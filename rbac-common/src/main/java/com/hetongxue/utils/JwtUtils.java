package com.hetongxue.utils;

import com.hetongxue.lang.Const;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: JWT工具类
 * @ClassNmae: JwtUtils
 * @Author: 何同学
 * @DateTime: 2022-05-25 22:01
 **/
@Component
public class JwtUtils {

    /**
     * 生成JWT
     */
    public String generateToken(String username) {
        Date nowDate = new Date();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(new Date(nowDate.getTime() * 1000 * 60 * 60 * 24 * Const.EXPIRE))// 7天过期
                .signWith(SignatureAlgorithm.HS512, Const.SECRET)
                .compact();
    }

    /**
     * 解析JWT
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(Const.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | SignatureException | UnsupportedJwtException | MalformedJwtException |
                 IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 判断jwt是否过期
     */
    public boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}