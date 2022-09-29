package com.example.permissionsystem.filter;

import com.alibaba.fastjson.JSON;
import com.example.permissionsystem.domain.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter {
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public TokenFilter(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        log.info("请求头的token：{}",token);
        String key = "TOKEN:"+token;
        if (StringUtils.hasText(token)){
            String user = stringRedisTemplate.opsForValue().get(key);
            MyUserDetails userDetails = JSON.parseObject(user, MyUserDetails.class);
            if (null!=user && null == SecurityContextHolder.getContext().getAuthentication()){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
