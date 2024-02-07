package com.kimu.dicamsi.newdichamsi.config.filter;

import com.kimu.dicamsi.newdichamsi.config.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 로그인 정보 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        System.out.println(request);
        System.out.println(username+" "+password);
        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password,null);
        //검증을 위해 매니저에 토큰을 담는다
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("로그인성공");

        String jwtToken = jwtUtils.generateJwtToken(authResult);
        System.out.println(jwtToken);
        response.addHeader("Authorization","Bearer "+ jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(401,"로그인오류");
        System.out.println("dd");
    }



}
