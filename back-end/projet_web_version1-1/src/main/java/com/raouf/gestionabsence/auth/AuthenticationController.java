package com.raouf.gestionabsence.auth;

import com.raouf.gestionabsence.entities.User;
import com.raouf.gestionabsence.exception.UserNotFoundException;
import com.raouf.gestionabsence.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/find/byEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        Optional<User> userOptional = Optional.ofNullable(service.findUserByEmail(email));
        // Check if the user exists and return it, or handle the case where the user is not found
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
