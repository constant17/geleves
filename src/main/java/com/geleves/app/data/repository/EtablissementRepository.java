package com.geleves.app.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geleves.app.data.entity.Etablissement;

public interface EtablissementRepository extends JpaRepository<Etablissement, Integer> {
	
	
	
}
