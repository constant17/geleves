package com.geleves.app.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Eleve extends AbstractEntity{
	
	
	
	 	@NotEmpty
	 	private String prenom = "";

	    @NotEmpty
	    private String nom = "";
	    
		private String addresse = "";
		
		private LocalDate dateDeNaissance = null;

	    @ManyToOne
	    @JoinColumn(name = "parent_id")
	    @NotNull
	    private Parent parent;
	    
	    @ManyToOne
	    @JoinColumn(name = "niveau_id")
	    @NotNull
	    private Niveau niveau;

	    @NotNull
	    private String statut;
	    
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


	    public String getStatut() {
	        return statut;
	    }

	    public void setStatut(String status) {
	        this.statut = status;
	    }
	    
	    public Niveau getNiveau() {
			return niveau;
		}

		public void setNiveau(Niveau niveau) {
			this.niveau = niveau;
		}

		public String getAddresse() {
			return addresse;
		}

		public void setAddresse(String addresse) {
			this.addresse = addresse;
		}

		public Parent getParent() {
			return parent;
		}

		public void setParent(Parent parent) {
			this.parent = parent;
		}

		public LocalDate getDateDeNaissance() {
			return dateDeNaissance;
		}

		public void setDateDeNaissance(LocalDate date_de_naissance) {
			this.dateDeNaissance = date_de_naissance;
		}
		

}
