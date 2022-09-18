package com.example.fundoonotemicroservice.service;

import com.example.fundoonotemicroservice.dto.LabelDTO;
import com.example.fundoonotemicroservice.exception.NoteNotFound;
import com.example.fundoonotemicroservice.model.LabelModel;
import com.example.fundoonotemicroservice.repository.LabelRepository;
import com.example.fundoonotemicroservice.repository.NoteRepository;
import com.example.fundoonotemicroservice.util.Response;
import com.example.fundoonotemicroservice.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * purpose-Implementation of label operation API'S.
 * @author anuj solanki
 * @date 14/09/2022
 * @version 1.0
 */


@Service
public class LabelService implements ILabelService{

    @Autowired
    LabelRepository labelRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    Token tokenUtil;

    @Autowired
    NoteService noteService;

    /**
     * purpose-Implementing logic of API for crating label.
     * @param token
     * @param noteId
     * @param labelDTO
     * @return label details.
     */
    @Override
    public Response creatLabel(String token,long noteId,LabelDTO labelDTO) {
        LabelModel labelModel=new LabelModel(labelDTO);
        labelModel.setUserId(tokenUtil.decodeToken(token));
        labelModel.setNoteId(noteId);
        labelModel.setRegisterDate(LocalDateTime.now());
        labelRepository.save(labelModel);

        return new Response("Adding label ",200,labelModel);
    }

    @Override
    public Response readLabel(String token,long labelId) {
        Long userId= tokenUtil.decodeToken(token);
        if (noteService.isUserPresent(userId)!=null){
             Optional<LabelModel> labelModel= labelRepository.findById(labelId);
             return new Response("Fetching Label",200,labelModel);
        }
        throw new NoteNotFound(400,"User Note Found !");
    }

    @Override
    public Response updateLabel(String token, long labelId, LabelDTO labelDTO) {
        Long userId= tokenUtil.decodeToken(token);
        if (noteService.isUserPresent(userId)!=null){
            Optional<LabelModel> labelModel= labelRepository.findById(labelId);
            if (labelModel.isPresent()) {
                labelModel.get().setLabelName(labelDTO.getLabelName());
                labelModel.get().setUpdateDate(LocalDateTime.now());
                return new Response("Updating Label", 200, labelModel.get());
            }
            throw new NoteNotFound(400,"Label Note Found !");
        }
        throw new NoteNotFound(400,"User Note Found !");
    }

    @Override
    public Response deleteLabel(String token, long labelId) {
        Long userId= tokenUtil.decodeToken(token);
        if (noteService.isUserPresent(userId)!=null){
            Optional<LabelModel> labelModel= labelRepository.findById(labelId);
            if (labelModel.isPresent()) {
                labelRepository.delete(labelModel.get());
                return new Response("Deleting Label", 200, labelModel.get());
            }
            throw new NoteNotFound(400,"Label Note Found !");
        }
        throw new NoteNotFound(400,"User Note Found !");
    }
}
