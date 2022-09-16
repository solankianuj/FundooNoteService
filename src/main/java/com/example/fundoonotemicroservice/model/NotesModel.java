package com.example.fundoonotemicroservice.model;

import com.example.fundoonotemicroservice.dto.FundooNoteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "fundooNoteData")
@AllArgsConstructor
@NoArgsConstructor
public class NotesModel {
    @Id
    @GenericGenerator(name = "fundooNoteData",strategy = "increment")
    @GeneratedValue(generator = "fundooNoteData")
    @Column(name = "noteId")
    private Long noteId;
    private String tittle;
    private String description;
    private long userId;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private boolean trash;
    private boolean isArchive;
    private boolean pin;
    private long labelId;
    private String emailId;
    private String colour;
    private LocalDate reminderTime;

    @ManyToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<LabelModel> labelList;
    @ElementCollection
    List<String> collaborator;


    public NotesModel(FundooNoteDTO fundooNoteDTO) {
        this.tittle= fundooNoteDTO.getTittle();
        this.description= fundooNoteDTO.getDescription();
        this.emailId= fundooNoteDTO.getEmailId();
        this.colour= fundooNoteDTO.getColour();
    }




}
