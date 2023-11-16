package com.raouf.gestionabsence.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.raouf.gestionabsence.token.TokenRepository;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException  {

            if (request.getServletPath().contains("/api/v1/auth")) {
                System.out.println("555hhhhhhhhhhhhhhhhhhh");
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                System.out.println("Bearer Bearer Bearer Bearer");
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail);
            System.out.println("kkkkkkkkkkkkkk");
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("User roles: " + userDetails.getAuthorities());
                System.out.println(userDetails);
                System.out.println("jjjjjjjjjjjjj");
                var isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                System.out.println(isTokenValid);
                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    System.out.println("xxxxxxxxxxxxxxx");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    System.out.println(authToken);
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            System.out.println("authToken authToken authToken authToken authToken authToken");
            filterChain.doFilter(request, response);
        }
}
