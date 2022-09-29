package com.example.permissionsystem.handler;

import com.alibaba.fastjson.JSON;
import com.example.permissionsystem.domain.MyUserDetails;
import com.example.permissionsystem.utils.JwtUtils;
import com.example.permissionsystem.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private StringRedisTemplate stringRedisTemplate;



    @Autowired
    public AuthenticationSuccessHandler(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Integer id = userDetails.getId();
        String token = JwtUtils.createToken(username, id);
        stringRedisTemplate.opsForValue().set("TOKEN:"+token, JSON.toJSONString(userDetails),60, TimeUnit.MINUTES);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.ok(token,"success")));
        writer.flush();
        writer.close();
    }
}
