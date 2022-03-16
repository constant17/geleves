package com.geleves.app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geleves.app.data.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{

}
