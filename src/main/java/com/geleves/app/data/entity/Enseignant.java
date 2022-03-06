package com.geleves.app.data.entity;



import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Formula;

import com.geleves.app.data.AbstractEntity;


@Entity
public class Enseignant extends AbstractEntity {
	
    @NotBlank
    private String nom;
    
    @NotBlank
    private String prenom;
    
    @NotBlank
    private String contact;
    
    @Email
    private String email;

    //  Replacer par les classes et matieres
    @OneToMany(mappedBy="enseignant")
    private List<Cours> cours = new LinkedList<>();
    
    @ManyToMany
    @JoinTable(
      name = "enseignant_classes", 
      joinColumns = @JoinColumn(name = "enseignant_id", referencedColumnName = "id",
      nullable = false, updatable = false), 
      inverseJoinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id",
      nullable = false, updatable = false))
    private List<Classe> classes = new LinkedList<>();
    
    @Formula("(select count(cr.id) from Cours cr where cr.enseignant_id = id)")
    private int nombreDeCours;
    
   //@Formula("(select count(classe_id) from classe_enseignants where enseignant_id = id)")
    private int nombreDeClasses;

    @Override
    public String toString() {
        return nom+" "+prenom;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
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

	public List<Cours> getCours() {
		return cours;
	}

	public void setCours(List<Cours> cours) {
		this.cours = cours;
	}

	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public int getNombreDeCours() {
		return nombreDeCours;
	}

	public int getNombreDeClasses() {
		return nombreDeClasses;
	}
	public void setNombreDeClasses(int nbr) {
		nombreDeClasses = nbr;
	}
	
    
    
}
