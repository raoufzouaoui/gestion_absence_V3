package com.raouf.gestionabsence.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

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
@Table(name = "T_JUSTIFICATION")
public class Justification implements Serializable{

	public Justification() {
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="JUST_ID")
	Long id ;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
	@Temporal(TemporalType.DATE)
	Date Date_deb;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
	@Temporal(TemporalType.DATE)
	Date date_fin;

	@Column(name="JUST_COM")
	String description ;
	@OneToOne
	Absence absence ;

}
