package com.geleves.app.data.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Acteur extends AbstractEntity{
	

 	@NotEmpty
 	private String prenom = "";

    @NotEmpty
    private String nom = "";
    
    @NotEmpty
    private String role="";
    
    @OneToMany(mappedBy="responsable")
    private List<Activite> activites = new LinkedList<>();
    
	@Override
    public String toString() {
        return nom+" "+prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
	
}
