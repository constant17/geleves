package com.geleves.app.data.entity;


import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Formula;

import com.geleves.app.data.AbstractEntity;


@Entity
public class Classe extends AbstractEntity {
	
    @NotBlank
    private String nom;
    
    @NotBlank
    private String niveau;
    
    
    @ManyToMany(mappedBy = "classes", fetch = FetchType.LAZY)
    private List<Enseignant> enseignants = new LinkedList<>();
    
    @OneToMany(mappedBy="classe")
    private List<Cours> cours = new LinkedList<>();
    
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

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

    
	public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

	public List<Cours> getCours() {
		return cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

   
    
}
