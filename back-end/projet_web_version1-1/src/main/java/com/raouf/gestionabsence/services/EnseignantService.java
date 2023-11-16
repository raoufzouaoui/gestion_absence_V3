package com.raouf.gestionabsence.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raouf.gestionabsence.auth.AuthenticationRequest;
import com.raouf.gestionabsence.auth.AuthenticationResponse;
import com.raouf.gestionabsence.auth.RegisterRequest;
import com.raouf.gestionabsence.entities.User;
import com.raouf.gestionabsence.exception.UserNotFoundException;
import com.raouf.gestionabsence.repository.UserRepository;
import com.raouf.gestionabsence.services.auth.EnseignantUserDetailsService;
import com.raouf.gestionabsence.token.Token;
import com.raouf.gestionabsence.token.TokenRepository;
import com.raouf.gestionabsence.token.TokenType;
import com.raouf.gestionabsence.config.JwtService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EnseignantService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;




    public EnseignantService(UserRepository repository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse authenticateTeacher(AuthenticationRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // Authenticate the teacher
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        System.out.println("xxxxxxxxxxxxxx");
        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate an access token and return it to the teacher
        var userDetails = repository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        System.out.println("333333333333333");
        System.out.println(userDetails);
        String accessToken = jwtService.generateToken(userDetails);
        System.out.println(accessToken);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        System.out.println(refreshToken);
        // Save the refresh token in the database
        saveUserToken(userDetails, accessToken);

        System.out.println("111111111111111111111111111111");
        System.out.println(loginRequest);
        // Return the access token and refresh token in the response
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(loginRequest.getEmail())
                .password(loginRequest.getPassword())
                .firstname(userDetails.getFirstname())
                .lastname(userDetails.getLastname())
                .role(userDetails.getRole())
                .id(userDetails.getId())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


}
