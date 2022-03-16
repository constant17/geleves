package com.geleves.app.data.entity;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Formula;

import com.geleves.app.data.AbstractEntity;


@Entity
public class Parent extends AbstractEntity {
	
    @NotBlank
    private String nom;
    
    @NotBlank
    private String prenom;
    
    @NotBlank
    private String contact;

    @NotBlank
    private String address;
    
    @Email
    @NotEmpty
    private String email;

    @OneToMany(mappedBy="parent", fetch = FetchType.EAGER)
    private Set<Eleve> enfants = new HashSet<Eleve>();
    
    @Formula("(select count(e.id) from Eleve e where e.parent_id = id)")
    private int nombreDEnfants;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	@Transactional
	public Set<Eleve> getEnfants() {
        return enfants;
    }

    public void setEnfants(Set<Eleve> enfants) {
        this.enfants = enfants;
    }

    public int getNombreDEnfants(){
        return nombreDEnfants;
    }
    
}
