package com.geleves.app.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geleves.app.data.entity.Cours;

public interface CoursRepository extends JpaRepository<Cours, Integer> {
	
	@Query("select c from Cours c " +
	        "where enseignant_id = ?1")
	    List<Cours> getCoursByEnseignant(@Param("enseignantId") int enseignantId);
	
	@Query("select c from Cours c " +
	        "where classe_id = ?1")
	    List<Cours> findCoursByClasse(@Param("classeId") int classeId);
}
