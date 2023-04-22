package com.example.demo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter

@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "T_ETUDIANT")
public class Etudiant implements Serializable {

	public Etudiant() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ETUD_ID")
	Long id ;
	@Column(name = "ETUD_CIN")
	Long cin ;
	@Column(name = "ETUD_NOM")
	String nom;
	@Column(name = "ETUD_PRENOM")
	String prenom;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
	@Temporal(TemporalType.DATE)
	Date date_naissance;

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
	@Column(name="ETUD_ROLE")
	String role ;

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	public Long getCin() {
		return cin;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Date getDate_naissance() {
		return date_naissance;
	}

	public float getAge() {
		return age;
	}
	//public String getAbsent() {return absent;}
	public Groupe getGroupe() {
		return groupe;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
}
