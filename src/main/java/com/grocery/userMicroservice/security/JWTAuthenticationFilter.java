package com.grocery.userMicroservice.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.util.UrlPathHelper;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.userMicroservice.dtos.Users;
import com.grocery.userMicroservice.utils.SecurityConstants;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private AuthenticationManager authenticationManager;
	private final static UrlPathHelper urlPathHelper = new UrlPathHelper();
	 
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(SecurityConstants.LOGIN_URL);
        this.authenticationManager = authenticationManager;
    }
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Users creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Users.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUserName(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain,
        Authentication auth) throws IOException, ServletException {
//    	SecurityContextHolder.getContext().setAuthentication(auth);
//
//        chain.doFilter(req, res);
    	Authentication user = auth;
        String token = JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
    
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//            AuthenticationException failed) throws IOException, ServletException {
//        logger.debug("failed authentication while attempting to access "
//                + urlPathHelper.getPathWithinApplication((HttpServletRequest) request));
//
//        //Add more descriptive message
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
//                "Authentication Failed");
//    }
}
