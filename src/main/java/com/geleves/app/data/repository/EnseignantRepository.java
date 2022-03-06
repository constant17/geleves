package com.geleves.app.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.geleves.app.data.entity.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {
	
	@Query("select e from Enseignant e " +
	        "where lower(e.prenom) like lower(concat('%', :searchTerm, '%')) " +
	        "or lower(e.nom) like lower(concat('%', :searchTerm, '%'))")
	    List<Enseignant> search(@Param("searchTerm") String searchTerm);

}
