package com.kimu.dicamsi.newdichamsi.config;

import com.kimu.dicamsi.newdichamsi.config.filter.LoginFilter;
import com.kimu.dicamsi.newdichamsi.service.CustomMemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomMemberDetailsService customMemberDetailsService;
    private final JwtUtils jwtUtils;
    private final CorsConfig corsConfig;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customMemberDetailsService);
//        authenticationProvider.setPasswordEncoder(encoder());
//        return authenticationProvider;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());
        http
                .cors(cors -> corsConfig.corsFilter());
        http
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/api/auth/login","/api/auth/join").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()//이 URL 경로 빼고는 다 인증이 필요함
                );
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtils),UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class); //UsernamePasswordAuthenticationFilter 전에 실행
//        http.authenticationProvider(authenticationProvider());
        return http.build();
    }


}
