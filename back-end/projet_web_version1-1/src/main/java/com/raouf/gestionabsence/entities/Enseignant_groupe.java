package com.raouf.gestionabsence.entities;

import java.io.Serializable;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString

@EqualsAndHashCode
@Table(name = "T_Enseignant_Groupe")
public class Enseignant_groupe implements Serializable {

	public Enseignant_groupe() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ENS_ID")
	Long id ;
	@Enumerated(EnumType.STRING)
	Groupe groupe ;
	@Column(name="ENS_EMAIL")
	String email ;
	@OneToOne
	Enseignant enseignant;
	

}
