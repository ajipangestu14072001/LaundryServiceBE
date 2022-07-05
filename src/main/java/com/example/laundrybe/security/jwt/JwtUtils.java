package com.example.laundrybe.security.jwt;


import com.example.laundrybe.security.services.KaryawanDetailsImpl;
import com.example.laundrybe.security.services.KonsumenDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${laundry.app.jwtSecret}")
    private String jwtSecret;

    @Value("${laundry.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${laundry.app.jwtCookieName}")
    private String jwtCookie;



    public String generateJwtToken(Authentication authentication) {

        KonsumenDetailsImpl konsumenDetails = (KonsumenDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((konsumenDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateJwtTokenKaryawan(Authentication authentication) {

        KaryawanDetailsImpl karyawanDetails = (KaryawanDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((karyawanDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }


    public ResponseCookie generateJwtCookie(KonsumenDetailsImpl konsumenDetails) {
        String jwt = generateTokenFromUsername(konsumenDetails.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtSecret, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("JWT tidak valid: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Masalah terjadi: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT kadaluarsa: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT tidak support: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT kosong: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
