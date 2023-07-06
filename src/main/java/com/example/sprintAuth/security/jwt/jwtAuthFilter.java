package com.example.sprintAuth.security.jwt;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class jwtAuthFilter extends OncePerRequestFilter 
{
    private final jwtService jwtservice;


    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response,
        FilterChain filterChain
        )
        throws ServletException, IOException {
            final String authHeader = request.getHeader("authentification");
            final String jwt;
            final String username;

            // Authorization: Bearer <access_token>
            if (authHeader == null || !authHeader.startsWith("bearer "))
            {
                filterChain.doFilter(request, response);
                return ;
            }

            // 7 because "Bearer " is 7 char before <acces_token>
            jwt = authHeader.substring(7);
           
             username = jwtservice.extractUsername(jwt);


     
        throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }

    
}
