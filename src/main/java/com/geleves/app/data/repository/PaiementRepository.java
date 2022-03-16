package com.geleves.app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geleves.app.data.entity.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Integer>{

}
