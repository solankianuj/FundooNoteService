package com.example.fundoonotemicroservice.service;

import com.example.fundoonotemicroservice.dto.LabelDTO;
import com.example.fundoonotemicroservice.model.NotesModel;
import com.example.fundoonotemicroservice.util.Response;

public interface ILabelService {
    Response creatLabel(String token,LabelDTO labelDTO);
    Response readLabel(String token,long noteId,long labelId);
    Response updateLabel(String token,long labelId,LabelDTO labelDTO);
    Response deleteLabel(String token,long labelId);
}
