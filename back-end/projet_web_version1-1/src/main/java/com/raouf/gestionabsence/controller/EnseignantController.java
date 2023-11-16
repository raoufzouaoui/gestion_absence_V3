package com.raouf.gestionabsence.controller;


import com.raouf.gestionabsence.auth.AuthenticationRequest;
import com.raouf.gestionabsence.auth.AuthenticationResponse;
import com.raouf.gestionabsence.config.JwtService;
import com.raouf.gestionabsence.entities.*;
import com.raouf.gestionabsence.repository.EnseignantRepository;
import com.raouf.gestionabsence.repository.UserRepository;
import com.raouf.gestionabsence.services.AbsenceService;
import com.raouf.gestionabsence.services.EnseignantService;
import com.raouf.gestionabsence.services.EtudiantService;
import com.raouf.gestionabsence.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/enseignant", method = RequestMethod.POST)
@RestController
@CrossOrigin("*")
public class EnseignantController {

    private JustificationController justificationController;
    private EnseignantService enseignantService;

    private MatiereController matiereController;

    private final EtudiantController etudiantController;

    private EnseignantRepository repo;
    private AbsenceController absenceController;

    public EnseignantController(JustificationController justificationController,AbsenceController absenceController, MatiereController matiereController,EnseignantService enseignantService, EtudiantController etudiantController,EnseignantRepository repo) {
        this.enseignantService=enseignantService;
        this.etudiantController=etudiantController;
        this.repo=repo;
        this.matiereController=matiereController;
        this.absenceController = absenceController;
        this.justificationController = justificationController;
    }

    /*@PostMapping("/register")
    public ResponseEntity<Enseignant> registerUser(@RequestBody Enseignant user) {
        System.out.println(user);
        Enseignant existingEmail = repo.findByEmail(user.getEmail());
        System.out.println(existingEmail);
        if (existingEmail == null){
             if (user.getPassword().equals(user.getConfirmPassword())) {
                return ResponseEntity.ok(repo.save(user));
            } else {
                 throw new IllegalArgumentException("Password and ConfirmPassword not the same");
            }
        } else {
            throw new IllegalArgumentException("Email already exists.");
        }
    }*/

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
//        System.out.println(username);
//        System.out.println(password);
        AuthenticationResponse response = enseignantService.authenticateTeacher(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findEnseignant/byEmail/{email}")
    public ResponseEntity<Enseignant> getEnseignantByEmail (@PathVariable("email") String  email) {
        Enseignant enseignant = repo.findByEmail(email);
        if (enseignant != null) {
            return ResponseEntity.ok(enseignant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
