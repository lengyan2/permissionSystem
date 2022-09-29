package com.example.permissionsystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.relational.core.sql.In;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static long TOKEN_EXPRIRATION = 24 * 60 * 60 * 1000;
    private static String  TOKEN_SECRET_KEY = "123456";

    /**
     * 创建token
     * @param username
     *
     * @return
     */
    public static String createToken(String username, Integer id){
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        Date exprirationDate = new Date(currentTimeMillis + TOKEN_EXPRIRATION);

        // 存放属性 比如权限
        Map<String,Object> claims = new HashMap<>();
        claims.put("username",username);
        claims.put("id",id);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg","RS256")
                .setClaims(claims)
                .setSubject("User").setIssuedAt(currentDate)
                .setExpiration(exprirationDate)
                .signWith(SignatureAlgorithm.HS512,TOKEN_SECRET_KEY)
                .compact();
    }
    /**
     * 判断token是否存在和有效
     *
     */
    public static boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){return  false;}
        Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(token);
        return true;
    }

    /**
     * 根据token获取username
     */
    public static String getIdByToken(String token){
        if (!StringUtils.hasText(token)){return "";}
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return (String) body.get("id");
    }
}
