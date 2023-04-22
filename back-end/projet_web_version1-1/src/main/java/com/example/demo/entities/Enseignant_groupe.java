package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
