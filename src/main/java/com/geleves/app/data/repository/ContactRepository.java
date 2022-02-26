package com.geleves.app.data.repository;

import com.geleves.app.data.entity.Contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("select c from Contact c " +
        "where lower(c.prenom) like lower(concat('%', :searchTerm, '%')) " +
        "or lower(c.nom) like lower(concat('%', :searchTerm, '%'))")
    List<Contact> search(@Param("searchTerm") String searchTerm);
}
