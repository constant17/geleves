package com.geleves.app.data.entity;

import com.geleves.app.data.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Formula;

@Entity
public class Niveau extends AbstractEntity {
	
    @NotBlank
    private String niveau;
    
    @NotBlank
    private String annee_scolaire;
 

    @ManyToMany(mappedBy = "niveaux", fetch = FetchType.LAZY)
    private List<Enseignant> enseignants = new LinkedList<>();
    
    @OneToMany(mappedBy = "niveau")
    private List<Cours> cours = new LinkedList<>();
    
    @Formula("(select count(e.id) from Eleve e where e.niveau_id = id)")
    private int nombreDEleves;

    @Override
    public String toString() {
        return niveau;
    }

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String nivo) {
		this.niveau = nivo;
	}

	public int getNombreDEleves() {
		return nombreDEleves;
	}

	public void setNombreDEleves(int nombreDEleves) {
		this.nombreDEleves = nombreDEleves;
	}
    
	public List<Enseignant> getEnseignants() {
        return enseignants;
    }

	public String getAnnee_scolaire() {
		return annee_scolaire;
	}

	public void setAnnee_scolaire(String annee_scolaire) {
		this.annee_scolaire = annee_scolaire;
	}

	public void setEnseignants(List<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}
    
    
}
