package com.example.permissionsystem.handler;

import com.example.permissionsystem.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未认证 统一处理
 */
@Component
public class MyAuthenticationEntryPoint  implements AuthenticationEntryPoint {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.error(501,"未登录请先登录")));
        writer.flush();
        writer.close();
    }
}
