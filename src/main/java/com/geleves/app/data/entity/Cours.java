package com.geleves.app.data.entity;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;


@Entity
public class Cours extends AbstractEntity {
	
    @NotBlank
    private String nom;
    
    @NotBlank
    private String niveau;
   
    private int nombreDHeuresParSemaine;
    
    private int coefficient;
    
    @NotBlank
    private String code;
    
    
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    @NotNull
    private Enseignant enseignant;
    
    @ManyToOne
    @JoinColumn(name = "classe_id")
    @NotNull
    private Classe classe;
    
    

    @Override
    public String toString() {
        return code+": "+nom;
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

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getNombreDHeuresParSemaine() {
		return nombreDHeuresParSemaine;
	}

	public void setNombreDHeuresParSemaine(int nombreHeures) {
		this.nombreDHeuresParSemaine = nombreHeures;
	}
    
	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	
    
}
