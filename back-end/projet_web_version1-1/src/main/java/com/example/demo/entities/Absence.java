package com.example.demo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "T_ABSENCES")
public class Absence implements Serializable{

	public Absence() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ABS_ID")
	Long id ;

	@JsonFormat(pattern="HH:mm", timezone = "UTC")
	@Temporal(TemporalType.TIME)
	Date heure;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
	@Temporal(TemporalType.DATE)
	Date date;

	@OneToOne
	Matiere matiere;
	@ManyToOne(cascade=CascadeType.ALL)
	Etudiant etudiant ;
	@ManyToOne(cascade=CascadeType.ALL)
	Enseignant enseignant ;
	@Column(name="justification")
	String justification;
	@Column(name="justifie")
	String justifie;
}
