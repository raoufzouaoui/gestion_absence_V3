package com.raouf.gestionabsence.auth;

import com.raouf.gestionabsence.entities.Groupe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String numero;
    private String password;
    private String confirmPassword;
    private String role;
    private Long cin;
    private Groupe groupe;
    private Date dateNaissance;
    private float age;

}
