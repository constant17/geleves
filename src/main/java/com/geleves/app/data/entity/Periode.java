package com.geleves.app.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import com.geleves.app.data.AbstractEntity;
import com.sun.istack.NotNull;

@Entity
public class Periode extends AbstractEntity{
	
	@NotNull
	private String anneeScolaire;
	
	@NotNull
	private byte trimestre;
	
	@NotNull
	private LocalDate dateDeRentree;
	
	@NotNull
	private LocalDate DateGrandeVacance;

	public String getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

	public byte getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(byte trimestre) {
		this.trimestre = trimestre;
	}

	public LocalDate getDateDeRentree() {
		return dateDeRentree;
	}

	public void setDateDeRentree(LocalDate dateDeRentree) {
		this.dateDeRentree = dateDeRentree;
	}

	public LocalDate getDateGrandeVacance() {
		return DateGrandeVacance;
	}

	public void setDateGrandeVacance(LocalDate dateGrandeVacance) {
		DateGrandeVacance = dateGrandeVacance;
	}
	
	
	

}
