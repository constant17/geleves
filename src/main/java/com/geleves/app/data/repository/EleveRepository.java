package com.geleves.app.data.repository;
import com.geleves.app.data.entity.Eleve;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EleveRepository extends JpaRepository<Eleve, Integer> {

	  @Query("select e from Eleve e " +
		        "where lower(e.prenom) like lower(concat('%', :searchTerm, '%')) " +
		        "or lower(e.nom) like lower(concat('%', :searchTerm, '%'))")
		    List<Eleve> search(@Param("searchTerm") String searchTerm);
	  
	  @Query("select e from Eleve e " +
		        "where niveau_id = ?1")
		    List<Eleve> findElevesByNiveau(@Param("niveauId") int niveauId);
	  
	  @Query("select e from Eleve e " +
		        "where classe_id = ?1")
		    List<Eleve> findElevesByClasse(@Param("classeId") int classeId);
	  
}
