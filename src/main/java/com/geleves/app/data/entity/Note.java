package com.geleves.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Note extends AbstractEntity{

	@NotNull
	private String note;
	
	@NotNull
	private String type_dexamen;
	
	private String commentaire;
	
	 @ManyToOne
	 @JoinColumn(name = "eleve_id")
	 @NotNull
	 private Eleve eleve;
	 
	 @ManyToOne
	 @JoinColumn(name = "cours_id")
	 @NotNull
	 private Cours matiere;
	
	@Override
	public String toString() {
		return type_dexamen+ " "+note;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getType_dexamen() {
		return type_dexamen;
	}

	public void setType_dexamen(String type_dexamen) {
		this.type_dexamen = type_dexamen;
	}

	public Eleve getEleve() {
		return eleve;
	}

	public void setEleve(Eleve eleve) {
		this.eleve = eleve;
	}

	public Cours getMatiere() {
		return matiere;
	}

	public void setMatiere(Cours matiere) {
		this.matiere = matiere;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	
}
