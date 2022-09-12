package com.example.fundoonotemicroservice.service;

import com.example.fundoonotemicroservice.dto.LabelDTO;
import com.example.fundoonotemicroservice.exception.NoteNotFound;
import com.example.fundoonotemicroservice.model.LabelModel;
import com.example.fundoonotemicroservice.model.NotesModel;
import com.example.fundoonotemicroservice.repository.LabelRepository;
import com.example.fundoonotemicroservice.util.Response;
import com.example.fundoonotemicroservice.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LabelService implements ILabelService{

    @Autowired
    LabelRepository labelRepository;
    @Autowired
    Token tokenUtil;

    NoteService noteService;

    @Override
    public Response creatLabel(String token,LabelDTO labelDTO) {
        LabelModel labelModel=new LabelModel(labelDTO);
        NotesModel notesModel=new NotesModel();
        labelModel.setUserId(tokenUtil.decodeToken(token));
        labelModel.setNoteId(notesModel.getNoteId());
        labelModel.setRegisterDate(LocalDateTime.now());
        labelRepository.save(labelModel);

        return new Response("Adding label ",200,labelModel);
    }

    @Override
    public Response readLabel(String token,long noteId, long labelId) {
        if (noteService.isUserPresent(token)){
            if (noteService.noteRepository.findById(noteId).isPresent()){
             Optional<LabelModel> labelModel= labelRepository.findById(labelId);
             return new Response("Fetching Label",200,labelModel);
            }
            throw new NoteNotFound(400,"Note Note Found !");
        }
        throw new NoteNotFound(400,"User Note Found !");

    }

    @Override
    public Response updateLabel(String token, long labelId, LabelDTO labelDTO) {
        return null;
    }

    @Override
    public Response deleteLabel(String token, long labelId) {
        return null;
    }
}
