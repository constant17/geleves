package com.geleves.app.data.repository;

import com.geleves.app.data.entity.Parent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
	
	@Query("select p from Parent p " +
	        "where lower(p.prenom) like lower(concat('%', :searchTerm, '%')) " +
	        "or lower(p.nom) like lower(concat('%', :searchTerm, '%'))")
	    List<Parent> search(@Param("searchTerm") String searchTerm);

}
