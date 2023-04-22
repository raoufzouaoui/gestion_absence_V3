package com.example.demo.controller;


import com.example.demo.entities.Enseignant;
import com.example.demo.entities.Etudiant;
import com.example.demo.repository.EnseignantRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/user", method = RequestMethod.POST)
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EnseignantController {

    @Autowired
    private EnseignantRepository repo;
    @PostMapping("/register")
    public ResponseEntity<Enseignant> registerUser(@RequestBody Enseignant user) {
        Enseignant existingEmail = repo.findByEmail(user.getEmail());
        System.out.println(user);
        if (existingEmail == null){
             if (user.getPassword().equals(user.getConfirmPassword())) {
                return ResponseEntity.ok(repo.save(user));
            } else {
                 throw new IllegalArgumentException("Password and ConfirmPassword not the same");
            }
        } else {
            throw new IllegalArgumentException("Email already exists.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Enseignant> loginUser(@RequestBody Enseignant enseignant) {
        Enseignant user = repo.findByEmail(enseignant.getEmail());
        if(user.getPassword().equals(enseignant.getPassword())){
            return ResponseEntity.ok(user);
        }
        return (ResponseEntity<Enseignant>) ResponseEntity.internalServerError();
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<Enseignant> getEnseignantByEmail (@PathVariable("email") String  email) {
        Enseignant enseignant = repo.findByEmail(email);
        if (enseignant != null) {
            return ResponseEntity.ok(enseignant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
