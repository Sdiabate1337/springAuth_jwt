package com.example.sprintAuth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.sprintAuth.Repository.userRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class applicationConfig {

    private final userRepository repository;

    @Bean
    public  UserDetailsService userDetailsService(){

        return username -> repository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
    
    @Bean AuthenticationProvider   authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(null);
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
