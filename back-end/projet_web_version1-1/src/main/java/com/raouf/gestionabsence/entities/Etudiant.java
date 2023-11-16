package com.raouf.gestionabsence.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "T_ETUDIANT")
public class Etudiant implements UserDetails {
	public Etudiant() {
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ETUD_ID")
	Long id ;
	@Column(name = "ETUD_CIN")
	Long cin ;
	@Column(name = "ETUD_NOM")
	String firstname;
	@Column(name = "ETUD_PRENOM")
	String lastname;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
	@Temporal(TemporalType.DATE)
	Date dateNaissance;
	@Column(name="ETUD_NUMERO")
	String numero ;
	@Transient
	float age;
	@Enumerated(EnumType.STRING)
	Groupe groupe;
	@Column(name="ETUD_EMAIL")
	String email ;
	@Column(name="ETUD_PASSWORD")
	String password ;
	@Column(name="ETUD_CONFIRMPASSWORD")
	String confirmPassword ;
	@Column(name="ROLE")
	String role;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
