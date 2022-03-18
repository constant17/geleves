package com.geleves.app.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.geleves.app.data.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

	@Query("select n from Note n " +
	        "where eleve_id = ?1")
	    List<Note> findNotesByEleve(@Param("eleveId") int eleveId);
}
