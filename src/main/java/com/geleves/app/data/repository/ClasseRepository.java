package com.geleves.app.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geleves.app.data.entity.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Integer>{

	 @Query("select c from Classe c " +
		        "where niveau_id = ?1")
		    List<Classe> findClassesByNiveau(@Param("niveauId") int niveauId);
}
