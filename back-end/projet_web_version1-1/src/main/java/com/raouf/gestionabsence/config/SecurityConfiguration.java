package com.raouf.gestionabsence.config;

import com.raouf.gestionabsence.entities.Role;
import com.raouf.gestionabsence.repository.UserRepository;
import com.raouf.gestionabsence.services.auth.EnseignantUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final ApplicationConfig applicationConfig;
    public static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("999hhhhhhhhhhhhhhhhhhh");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                .requestMatchers("/api/v1/enseignant/login").permitAll()
                               // .requestMatchers("/api/v1/etudiant/**").hasAnyRole("ETUDIANT", "ENSEIGNANT")
                                .requestMatchers("/api/v1/etudiant/**").permitAll()
                                .requestMatchers("/api/v1/enseignant/**").hasAnyRole("ENSEIGNANT")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(applicationConfig.authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class); // Add this line

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from the specified origin (http://localhost:4200)
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        config.addAllowedMethod(HttpMethod.OPTIONS);
        // Allow the following HTTP methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));

        // Allow the following headers in the request
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));

        // Allow credentials (cookies, authentication)
        config.setAllowCredentials(true);

        // Set the max age (in seconds) for this CORS configuration
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        // Log CORS configuration details
        logger.info("CORS Filter is applied with the following configuration:");
        logger.info("Allowed Origins: {}", config.getAllowedOrigins());
        logger.info("Allowed Headers: {}", config.getAllowedHeaders());
        logger.info("Allowed Methods: {}", config.getAllowedMethods());
        return new CorsFilter(source);
    }

}

