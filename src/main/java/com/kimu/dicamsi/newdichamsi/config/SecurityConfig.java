package com.kimu.dicamsi.newdichamsi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()//기본설정을 해제함
                .csrf().disable()//RestAPI는 쿠키를 쓰지 않기 때문에 해제함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//RESTAPI는 세션에 의존하지 않기때문에 세션 생성 안함
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()//로그인 요청은 누구나 허용
                .antMatchers(HttpMethod.POST,"/api/join/**").permitAll()//가입 요청은 누구나 허용
                .antMatchers(HttpMethod.GET,"/api/public/**").permitAll()//로그인 하지 않은 유저 콘텐츠 허용 주소
                .anyRequest().hasRole("USER");//나머지는 다 회원만 가능
    }
}
