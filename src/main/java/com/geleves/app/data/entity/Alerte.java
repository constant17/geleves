package com.geleves.app.data.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@OneToMany
	@JoinColumn(name = "parent_id")
	private Set<Parent> parent_concernes;
	
	@NotNull
	@OneToMany
	@JoinColumn(name = "acteur_id")
	private Set<Acteur> acteurs_concernes;
	
	@NotNull
	@OneToMany
	@JoinColumn(name = "enseignant_id")
	private Set<Enseignant> enseignants_concernes;

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

	public Set<Parent> getParentsConcernes() {
		return parent_concernes;
	}

	public void setConcerne(Set<Parent> concernes) {
		this.parent_concernes = concernes;
	}
	
	

}
