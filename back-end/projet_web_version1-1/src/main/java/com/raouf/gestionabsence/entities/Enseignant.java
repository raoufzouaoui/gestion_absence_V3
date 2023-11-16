package com.raouf.gestionabsence.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "T_ENSEIGNANT")

public class Enseignant implements UserDetails {

	public Enseignant() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ENS_ID")
	Long id ;
	@Column(name="ENS_NOM")
	String firstname ;
	@Column(name="ENS_PRENOM")
	String lastname ;
	@Column(name="ENS_EMAIL")
	String email ;
	@Column(name="ENS_NUMERO")
	String numero ;
	@Column(name="ENS_PASSWORD")
	String password ;
	@Column(name="ENS_CONFIRMPASSWORD")
	String confirmPassword ;
	@Column(name="ROLE")
	String role ;

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