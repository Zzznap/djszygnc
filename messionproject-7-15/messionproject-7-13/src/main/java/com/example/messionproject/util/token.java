package com.example.messionproject.util;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.UUID;

public class token {
    @org.junit.Test
    public static String sign(String temppp){//更改成User user
        String token;
        String signature="0x3f3f3f";
        JwtBuilder jwtBuilder = Jwts.builder();
        token= jwtBuilder.setHeaderParam("typ","JWT")//header
                .setHeaderParam("alg","HS256")
                .claim("Username",temppp)//payload   更改成user.getname
                .setSubject("token_")
                .setExpiration(new Date(System.currentTimeMillis()+30*60*1000))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return token;
    }
    @org.junit.Test
    public static String design(String temqqq){
        JwtParser jwtParser =Jwts.parser();
        String signature="0x3f3f3f";
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(temqqq);
        Claims claims = claimsJws.getBody();
        return claims.get("Username").toString();
    }
}
