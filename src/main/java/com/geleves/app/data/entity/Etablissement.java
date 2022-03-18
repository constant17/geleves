package com.geleves.app.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;

import com.geleves.app.data.AbstractEntity;
import com.sun.istack.NotNull;

@Entity
public class Etablissement extends AbstractEntity{

	@NotNull
	private String nom;
	
	@NotNull
	private String adresse;
	
	@NotNull
	private String contact;
	
	@NotNull
	@Email
	private String email;
	
	private int BP;
	
	private String logoPath;
	
	@Override
	public String toString() {
		return nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBP() {
		return BP;
	}

	public void setBP(int bP) {
		BP = bP;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	
}
