package com.example.sprintAuth.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private final UserDetailsService  userDetailsService;


    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response,
        FilterChain filterChain
        )
        throws ServletException, IOException {
            final String authHeader = request.getHeader("authentification");
            final String jwt;
            final String userEmail;

            // Authorization: Bearer <access_token>
            if (authHeader == null || !authHeader.startsWith("bearer "))
            {
                filterChain.doFilter(request, response);
                return ;
            }

            // 7 because "Bearer " is 7 char before <acces_token>
            jwt = authHeader.substring(7);
            userEmail = jwtservice.extractUsername(jwt);
            // chech is not authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                //userdetail fron database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // check if token generated is valid
                if (jwtService.isTokenValid(jwt, userDetails))
                {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getAuthorities()
                        );
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            filterChain.doFilter(request, response);


     
        throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
    }

    
}
