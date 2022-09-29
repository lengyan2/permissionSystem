package com.example.permissionsystem.handler;

import com.example.permissionsystem.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public MyLogoutSuccessHandler(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader("token");
        stringRedisTemplate.delete("TOKEN:"+token);
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.ok(200,"登出成功")));
        writer.flush();
        writer.close();
    }
}
