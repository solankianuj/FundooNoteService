package com.example.fundoonotemicroservice.service;

import com.example.fundoonotemicroservice.dto.FundooNoteDTO;
import com.example.fundoonotemicroservice.util.Response;


public interface INoteService {

    Response creatNote(FundooNoteDTO fundooNoteDTO);
    Response updateNote(String token,long noteId,FundooNoteDTO fundooNoteDTO);
    Response readNote(String token,long noteId);
    Response readAllNote(String token);
    Response trashNote(String token,long noteId);
    Response allTrashNote(String token);
    Response pinNote(String token,long noteId);
    Response unPinNote(String token,long noteId);
    Response allPinNote(String token);
    Response archiveNote(String token, long noteId);
    Response unArchiveNote(String token, long noteId);
    Response allArchiveNote(String  token);
    Response restoreNote(String token,long noteId);
    Response permanentlyDeleteNote(String token,long noteId);
    Response changeColourOfNote(String token,long noteId,String colour);
}
