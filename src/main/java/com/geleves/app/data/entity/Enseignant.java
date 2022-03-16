package com.geleves.app.data.entity;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
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


    @OneToMany(mappedBy="enseignant", fetch = FetchType.EAGER)
    private Set<Cours> cours = new HashSet<Cours>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "enseignant_classes", 
      joinColumns = @JoinColumn(name = "enseignant_id", referencedColumnName = "id",
      nullable = false, updatable = false), 
      inverseJoinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id",
      nullable = false, updatable = false))
    private Set<Classe> classes = new HashSet<Classe>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "enseignant_niveaux", 
      joinColumns = @JoinColumn(name = "enseignant_id", referencedColumnName = "id",
      nullable = false, updatable = false), 
      inverseJoinColumns = @JoinColumn(name = "niveau_id", referencedColumnName = "id",
      nullable = false, updatable = false))
    private Set<Niveau> niveaux = new HashSet<Niveau>();
    
    @Formula("(select count(cr.id) from Cours cr where cr.enseignant_id = id)")
    private int nombreDeCours;
    
   @Formula("(select count(ec.classe_id) from enseignant_classes ec where ec.enseignant_id = id)")
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
	@Transactional
	public Set<Cours> getCours() {
		return cours;
	}

	public void setCours(Set<Cours> cours) {
		this.cours = cours;
	}

	@Transactional
	public Set<Classe> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classe> classes) {
		this.classes = classes;
	}

	public int getNombreDeCours() {
		return nombreDeCours;
	}

	public int getNombreDeClasses() {
		return nombreDeClasses;
	}
	@Transactional
	public Set<Niveau> getNiveaux() {
		return niveaux;
	}

	public void setNiveaux(Set<Niveau> niveaux) {
		this.niveaux = niveaux;
	}
	
    
    
}
