package com.geleves.app.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Activite extends AbstractEntity {

    @NotEmpty
    private String nom = "";

    @NotNull
    @ManyToOne
    @JoinColumn(name = "acteur_id")
    @NotNull
    private Acteur responsable;
    
    @NotNull
    private LocalDate startingDate;
    
    @NotNull
    private LocalDate endingDate;

    @NotNull
    private String description;


    @Override
    public String toString() {
        return  nom;
    }

   

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



	public Acteur getResponsable() {
		return responsable;
	}



	public void setResponsable(Acteur responsable) {
		this.responsable = responsable;
	}



	public LocalDate getStartingDate() {
		return startingDate;
	}



	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}



	public LocalDate getEndingDate() {
		return endingDate;
	}



	public void setEndingDate(LocalDate endingDate) {
		this.endingDate = endingDate;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}
    
    
   

}
