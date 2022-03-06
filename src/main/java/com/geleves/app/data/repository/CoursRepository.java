package com.geleves.app.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geleves.app.data.entity.Contact;
import com.geleves.app.data.entity.Cours;
import com.geleves.app.data.entity.Eleve;

public interface CoursRepository extends JpaRepository<Cours, Integer> {
	
	@Query("select c from Cours c " +
	        "where enseignant_id = enseigantId")
	    List<Cours> getCoursByEnseignant(@Param("enseignantId") int enseignantId);
}
