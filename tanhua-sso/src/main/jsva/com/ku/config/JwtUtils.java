package com.ku.config;

import com.ku.common.JwtConstans;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * 私钥加密token
     *
     * @param map      载荷中的数据
     * @param secret   盐
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<String,Object> map, String secret, int expireMinutes) {
        Map<String,Object> header = new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256");
        return Jwts.builder()
                .setHeader(header)
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+expireMinutes*60*1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param secret 盐
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param secret 盐
     * @return 用户信息
     * @throws Exception
     */
    public static Map<String,Object> getInfoFromToken(String token, String secret) {
        Jws<Claims> claimsJws = parserToken(token, secret);
        Claims body = claimsJws.getBody();
        Map<String,Object> map = new HashMap<>();
        map.put(JwtConstans.JWT_KEY_Phone, body.get(JwtConstans.JWT_KEY_Phone));
        return map;
    }

}
