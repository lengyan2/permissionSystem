package com.example.permissionsystem.config;

import com.example.permissionsystem.filter.TokenFilter;
import com.example.permissionsystem.handler.*;
import com.example.permissionsystem.service.impl.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private StringRedisTemplate stringRedisTemplate;

    private MyUserService myUserService;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthticationFailureHandler authticationFailureHandler;

    private TokenFilter tokenFilter;

    @Autowired
    public WebSecurityConfig(MyUserService myUserService,AuthenticationSuccessHandler authenticationSuccessHandler
    , AuthticationFailureHandler authticationFailureHandler, TokenFilter tokenFilter){
        this.myUserService = myUserService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authticationFailureHandler = authticationFailureHandler;
        this.tokenFilter = tokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authticationFailureHandler)
                .and()
                .logout()
                .logoutSuccessHandler(new MyLogoutSuccessHandler(stringRedisTemplate))
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredSessionStrategy(new ExpriedSessionHandler());
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();

    }

    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
