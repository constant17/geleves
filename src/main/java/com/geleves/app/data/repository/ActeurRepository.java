package com.geleves.app.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geleves.app.data.entity.Acteur;
import com.geleves.app.data.entity.Eleve;
import com.geleves.app.data.entity.Enseignant;

public interface ActeurRepository extends JpaRepository<Acteur, Integer>{

	@Query("select e from Acteur e " +
	        "where lower(e.prenom) like lower(concat('%', :searchTerm, '%')) " +
	        "or lower(e.nom) like lower(concat('%', :searchTerm, '%'))")
	    List<Acteur> search(@Param("searchTerm") String searchTerm);
	
	
}
