package com.kimu.dicamsi.newdichamsi.config;

import com.kimu.dicamsi.newdichamsi.domain.Member.CustomMemberDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
public class JwtUtils {

    //JWT 시크릿키
    @Value("${dcs.app.jwtSecret}")
    private String jwtSecret;

    //만료 시간
    @Value("${dcs.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    //토큰 생성
    public String generateJwtToken(Authentication authentication) {
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        Claims claim = Jwts.claims();
        claim.put("Role",role);
        return Jwts.builder()
                .setSubject(customMemberDetails.getUsername())  //사용자정보
                .setClaims(claim)
                .setIssuedAt(new Date())  //발급일자
                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))  //현재 시간 + 유효 시간 => 만료 시간
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    //프로퍼티에 명시된 시크릿키를 한번 더 꼬아준다
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //토큰에서 사용자정보(username) 추출
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build() //시크릿키로 디코딩
                .parseClaimsJwt(token).getBody().getSubject();  //토큰 안에 있는 사용자 정보 추출
    }

//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
//            return true;
//        } catch (MalformedJwtException e) {
//        } catch (ExpiredJwtException e) {
//        } catch (UnsupportedJwtException e) {
//        } catch (IllegalArgumentException e) {
//        }
//        return false;
//    }

//    public String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7);
//        }
//        return null;
//    }
}
