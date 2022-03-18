package com.geleves.app.data.entity;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Formula;

import com.geleves.app.data.AbstractEntity;
import com.sun.istack.NotNull;


@Entity
public class Classe extends AbstractEntity {
	
    @NotBlank
    private String nom;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;
    
    @NotNull
    @Formula("(select count(e.id) from Eleve e where e.classe_id = id)")
    private int effectif;
    
    @OneToOne
    @JoinColumn(name = "chef_de_classe_id")
    private Eleve chefDeClasse;
    
    
    @ManyToMany(mappedBy = "classes", fetch = FetchType.LAZY)
    private Set<Enseignant> enseignants = new HashSet<Enseignant>();
    
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

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

    
	public Set<Enseignant> getEnseignants() {
        return enseignants;
    }

    public void setEnseignants(Set<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }

	public List<Cours> getCours() {
		return cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

	public int getEffectif() {return this.effectif;}

	public Eleve getChefDeClasse() {
		return chefDeClasse;
	}

	public void setChefDeClasse(Eleve chefDeClasse) {
		this.chefDeClasse = chefDeClasse;
	}
    
}
