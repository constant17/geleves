package com.geleves.app.data.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import com.geleves.app.data.AbstractEntity;

@Entity
public class Eleve extends AbstractEntity{
	
	 	@NotEmpty
	 	private String prenom = "";

	    @NotEmpty
	    private String nom = "";
	    
		private String addresse = "";
		
		private String matricule;
		
		@NotNull
		private LocalDate dateDeNaissance = null;
		
		@NotNull
		private LocalDate dateDInscription;
		

	    @ManyToOne
	    @JoinColumn(name = "parent_id")
	    @NotNull
	    private Parent parent;
	    
	    @ManyToOne
	    @JoinColumn(name = "niveau_id")
	    @NotNull
	    private Niveau niveau;
	    
	    @ManyToOne
	    @JoinColumn(name = "classe_id")
	    @NotNull
	    private Classe classe;
	    
	    /*@Transient
	    private int classeId = classe.getId();
	    
	    @Formula("(select c from Cours c where c.classe_id = classeId)")
	    private List<Cours> cours;*/
	    
	    @OneToMany(mappedBy="eleve", fetch = FetchType.EAGER)
	    private List<Note> notes;

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

		public Classe getClasse() {
			return classe;
		}

		public void setClasse(Classe classe) {
			this.classe = classe;
		}

		public String getMatricule() {
			return matricule;
		}

		public void setMatricule(String matricule) {
			this.matricule = matricule;
		}

		public LocalDate getDateDInscription() {
			return dateDInscription;
		}

		public void setDateDInscription(LocalDate dateDInscription) {
			this.dateDInscription = dateDInscription;
		}

		public List<Note> getNotes() {
			return notes;
		}
		

}
