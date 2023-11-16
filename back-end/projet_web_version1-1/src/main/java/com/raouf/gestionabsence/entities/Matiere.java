package com.raouf.gestionabsence.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;

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
@Table(name = "T_MATIERE")
public class Matiere implements Serializable{

	public Matiere() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MAT_ID")
	Long id ;
	@Column(name="MAT_NOM")
	String  nom ;
	@ManyToMany(cascade = CascadeType.ALL)
	Set<Enseignant> enseignant ;
}
