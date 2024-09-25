package com.gyh.usermsa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyh.usermsa.dto.UserDto;
import com.gyh.usermsa.service.UserService;
import com.gyh.usermsa.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RefreshScope
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final Environment environment;


    public AuthenticationFilter(AuthenticationManager authenticationManager,
        UserService userService, Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException {
        try {

            RequestLogin creds = new ObjectMapper().readValue(req.getInputStream(), RequestLogin.class);
            //DTO 검증
            if (creds.getEmail() == null || creds.getPassword() == null) {
                throw new AuthenticationServiceException("Email or Password is missing");
            }
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            //throw new RuntimeException(e);
            //구체적인 예외처리와 로그 추가
            logger.error("Error reading login request", e);
            throw new AuthenticationServiceException("unable to authenticate user");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
        Authentication auth) throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(userName);

        String token = createToken(userDetails);
        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getUserId());
    }

    private String createToken(UserDto userDetails) {

        byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();

        return Jwts.builder()
            .subject(userDetails.getUserId())
            .expiration(Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration_time")))))
            .issuedAt(Date.from(now))
            .signWith(secretKey)
            .compact();
    }
}
