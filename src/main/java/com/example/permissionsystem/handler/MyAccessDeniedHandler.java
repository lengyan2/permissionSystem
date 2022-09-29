package com.example.permissionsystem.handler;

import com.example.permissionsystem.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  统一权限处理
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.error(502,accessDeniedException.getMessage()+" -> 没有权限访问" )));
        writer.flush();
        writer.close();
    }
}
