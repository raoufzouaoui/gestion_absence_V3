package com.raouf.gestionabsence.auth;

import com.raouf.gestionabsence.entities.Etudiant;
import com.raouf.gestionabsence.exception.CINAlreadyExistsException;
import com.raouf.gestionabsence.exception.EmailAlreadyExistsException;
import com.raouf.gestionabsence.exception.UserNotFoundException;
import com.raouf.gestionabsence.repository.EtudiantRepository;
import com.raouf.gestionabsence.token.Token;
import com.raouf.gestionabsence.token.TokenRepository;
import com.raouf.gestionabsence.token.TokenType;
import com.raouf.gestionabsence.entities.User;
import com.raouf.gestionabsence.repository.UserRepository;
import com.raouf.gestionabsence.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final EtudiantRepository etudiantRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        Optional<Etudiant> etudiantEmail = etudiantRepository.findEtudiantByEmail(request.getEmail());
        if (etudiantEmail.isPresent()) {
            throw new EmailAlreadyExistsException("The provided email already exists.");
        }

        Optional<Etudiant> etudiantCin =etudiantRepository.findEtudiantByCin(request.getCin());

        if (etudiantCin.isPresent()) {
            throw new CINAlreadyExistsException("The provided CIN already exists.");
        }
        Etudiant etudiant = null;
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .ConfirmPassword(request.getConfirmPassword())
                .numero(request.getNumero())
                .build();

        System.out.println(request);
         etudiant = Etudiant.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .confirmPassword(request.getConfirmPassword())
                .numero(request.getNumero())
                .groupe(request.getGroupe())
                .dateNaissance(request.getDateNaissance())
                .age(calculateAge(request.getDateNaissance()))
                 .cin(request.getCin())
                .build();
                etudiantRepository.save(etudiant);

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .email(request.getEmail())
                .password(request.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .id(user.getId())
                .build();
    }

    private float calculateAge(Date dateNaissance) {
        // Convert the Date to a LocalDate
        LocalDate birthdate = dateNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Calculate the period between the birthdate and the current date
        Period period = Period.between(birthdate, currentDate);

        // Extract years, months, and days from the period
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // Calculate the age as a float (including months and days)
        float age = years + ((float) months / 12) + ((float) days / 365);

        return age;
}

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        //System.out.println("qqqqqqqqqqq");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //System.out.println(request);
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        System.out.println(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        System.out.println("sssssssssssss");

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .email(request.getEmail())
                .password(request.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .id(user.getId())
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

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public User findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        // Check if the user exists and return it, or handle the case where the user is not found
        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
