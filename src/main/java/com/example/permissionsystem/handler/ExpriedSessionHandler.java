package com.example.permissionsystem.handler;

import com.example.permissionsystem.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExpriedSessionHandler implements SessionInformationExpiredStrategy {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.error(503,"登录超时")));
        writer.flush();
        writer.close();
    }
}
