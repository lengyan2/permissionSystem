package com.example.permissionsystem.handler;

import com.example.permissionsystem.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthticationFailureHandler  implements AuthenticationFailureHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();

    // 登录失败
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.error(401,exception.getMessage()+"登录失败")));
        writer.flush();
        writer.close();
    }
}
