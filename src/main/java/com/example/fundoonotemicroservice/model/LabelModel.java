package com.example.fundoonotemicroservice.model;

import com.example.fundoonotemicroservice.dto.LabelDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "fundooLabelData")
@AllArgsConstructor
@NoArgsConstructor
public class LabelModel {
    @Id
    @GenericGenerator(name = "fundooLabelData",strategy = "increment")
    @GeneratedValue(generator = "fundooLabelData")
    private Long labelId;
    private String labelName;
    private Long userId;
    private Long noteId;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    @ManyToMany(mappedBy = "labelList")
    private List<NotesModel> notes;


    public LabelModel(LabelDTO labelDTO) {
        this.labelName=labelDTO.getLabelName();
    }
}
