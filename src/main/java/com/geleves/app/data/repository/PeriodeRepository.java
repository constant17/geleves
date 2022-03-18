package com.geleves.app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geleves.app.data.entity.Periode;

public interface PeriodeRepository extends JpaRepository<Periode,Integer> {

}
