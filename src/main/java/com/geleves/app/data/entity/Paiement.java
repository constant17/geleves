package com.geleves.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Paiement extends AbstractEntity{

	@NotNull
	private String period;
	
	@NotNull
	private int annee_scolaire;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "niveau_id")
	private Niveau niveau;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getAnnee_scolaire() {
		return annee_scolaire;
	}

	public void setAnnee_scolaire(int annee_scolaire) {
		this.annee_scolaire = annee_scolaire;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}
	
	
}
