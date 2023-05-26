package com.solera.backendcursojava.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solera.backendcursojava.SpringApplicationContext;
import com.solera.backendcursojava.exceptions.AuthenticationOwnException;
import com.solera.backendcursojava.models.requests.UserLoginRequestModel;
import com.solera.backendcursojava.services.UserServiceInterface;
import com.solera.backendcursojava.share.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),
                            userModel.getPassword(), new ArrayList<>()));
        }catch(IOException e){
            throw new AuthenticationOwnException("Error in attemptAuthentication");
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException{
        String username = ((User) authentication.getPrincipal()).getUsername(); // email
        //Create token
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        // Add header with public ID
        UserServiceInterface userService = (UserServiceInterface) SpringApplicationContext.getBean("userService"); // If the class is called "UserServices", the bean will be called "userService"
        // Create an interface method for the header
        UserDto userDto = userService.getUser(username); // remember: username is our email

        // Let know we are going to have other headers
        response.addHeader("Access-Control-Expose-headers", "Authorization, UserId");
        response.addHeader("UserId", userDto.getUserId());
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
}
