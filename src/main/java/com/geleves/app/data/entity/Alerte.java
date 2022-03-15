package com.geleves.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Alerte extends AbstractEntity{
	
	@NotNull
	private String titre;
	
	@NotNull
	private String message;
	
	@NotNull
	private String type_dalerte;
	
	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "concerne_id")
	private Parent concerne;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType_dalerte() {
		return type_dalerte;
	}

	public void setType_dalerte(String type_dalerte) {
		this.type_dalerte = type_dalerte;
	}

	public Parent getConcerne() {
		return concerne;
	}

	public void setConcerne(Parent concerne) {
		this.concerne = concerne;
	}
	
	

}
