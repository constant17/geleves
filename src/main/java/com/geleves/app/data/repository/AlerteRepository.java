package com.geleves.app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geleves.app.data.entity.Alerte;

public interface AlerteRepository extends JpaRepository<Alerte, Integer> {

}
