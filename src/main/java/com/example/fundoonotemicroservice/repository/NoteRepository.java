package com.example.fundoonotemicroservice.repository;

import com.example.fundoonotemicroservice.model.NotesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NotesModel,Long> {
}
