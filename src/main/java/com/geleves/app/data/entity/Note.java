package com.geleves.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Note extends AbstractEntity{

	@NotNull
	private float note;
	
	private String type_dexamen;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="periode_id")
	Periode periode;
	
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
		return String.format("%.2f", note);
	}

	public float getNote() {
		return note;
	}
	
	public String getCoefficientToString() {
		return String.valueOf(matiere.getCoefficient());
	}
	public String getTotalPoints() {
		return String.format("%.2f", (note * matiere.getCoefficient()));
	}
	public Enseignant getEnseignant() {
		return matiere.getEnseignant();
	}
	public void setNote(float note) {
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

	public Periode getPeriode() {
		return periode;
	}

	public void setPeriode(Periode periode) {
		this.periode = periode;
	}

	
	
}
