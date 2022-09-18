package com.example.fundoonotemicroservice.service;

import com.example.fundoonotemicroservice.dto.FundooNoteDTO;
import com.example.fundoonotemicroservice.exception.NoteNotFound;
import com.example.fundoonotemicroservice.model.LabelModel;
import com.example.fundoonotemicroservice.model.NotesModel;
import com.example.fundoonotemicroservice.repository.LabelRepository;
import com.example.fundoonotemicroservice.repository.NoteRepository;
import com.example.fundoonotemicroservice.service.mailService.MailServices;
import com.example.fundoonotemicroservice.util.Response;
import com.example.fundoonotemicroservice.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * purpose-note operation api's logics.
 * @author anuj solanki
 * @date 14/09/2022
 * @version 1.0
 */
@Service
public class NoteService implements  INoteService{

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    MailServices mailServices;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Token tokenUtil;

    /**
     * purpose-method for creating note.
     * @param fundooNoteDTO
     * @return note details.
     */
    @Override
    public Response creatNote(String token,FundooNoteDTO fundooNoteDTO) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            NotesModel notesModel = new NotesModel(fundooNoteDTO);
            notesModel.setRegisterDate(LocalDateTime.now());
            notesModel.setUserId(userId);
            noteRepository.save(notesModel);
            mailServices.send(notesModel.getEmailId(), "Note Registration. ", "Note added successfully.\n" + notesModel);
            return new Response("Note added successfully", 200, notesModel);
        }
        throw  new NoteNotFound(400,"user not found");
    }

    /**
     * purpose-method to update note.
     * @param token
     * @param noteId
     * @param fundooNoteDTO
     * @return
     */
    @Override
    public Response updateNote(String token,long noteId, FundooNoteDTO fundooNoteDTO) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel=noteRepository.findById(noteId);
            if (notesModel.isPresent()){
                if (notesModel.get().getUserId()==userId) {
                    notesModel.get().setTittle(fundooNoteDTO.getTittle());
                    notesModel.get().setDescription(fundooNoteDTO.getDescription());
                    notesModel.get().setEmailId(fundooNoteDTO.getEmailId());
                    notesModel.get().setColour(fundooNoteDTO.getColour());
                    notesModel.get().setUpdateDate(LocalDateTime.now());
                    noteRepository.save(notesModel.get());
                    return new Response("Note updated successfully", 200, notesModel.get());
                }
                throw  new NoteNotFound(400,"Note is not accessible.");
            }
            throw  new NoteNotFound(400,"Note not found.");
        }
        throw  new NoteNotFound(400,"user not found");
    }

    /**
     * purpose-method to read note.
     * @param token
     * @param noteId
     * @return
     */

    @Override
    public Response readNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
       if (isUserPresent(userId)!=null){
           Optional<NotesModel> notesModel=noteRepository.findById(noteId);
           if (notesModel.isPresent()){
               if (notesModel.get().getUserId()==userId){
               return new Response("Fetching Note Details By NoteId",200,notesModel.get());
               }
               throw  new NoteNotFound(400,"Note is not accessible.");
           }
           throw  new NoteNotFound(400,"Note not found.");
       }
        throw  new NoteNotFound(400,"user not found");
    }

    /**
     * purpose-method to read all note.
     * @param token
     * @return
     */

    @Override
    public Response readAllNote(String token) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            List<NotesModel> noteList=noteRepository.findAll().stream().filter(x->x.getUserId()==userId).collect(Collectors.toList());
            return new Response("Fetching all notes of user",200,noteList);
        }
        throw  new NoteNotFound(400,"user not found");
    }

    /**
     * purpose-method to move note in trash.
     * @param token
     * @param noteId
     * @return
     */

    @Override
    public Response trashNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel=noteRepository.findById(noteId);
            if (notesModel.isPresent()){
                if (notesModel.get().getUserId()==userId) {
                    if (notesModel.get().isPin() == false) {
                        notesModel.get().setTrash(true);
                        noteRepository.save(notesModel.get());
                        return new Response("Note moving into trash ", 200, notesModel.get());
                    }
                    throw  new NoteNotFound(400,"Note is Pined,Made it unpin first");
                }
                throw  new NoteNotFound(400,"Note is not accessible.");
            }
            throw  new NoteNotFound(400,"Note Not Found !");
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to restore note from trash.
     * @param token
     * @param noteId
     * @return
     */

    @Override
    public Response restoreNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.get().getUserId() == userId) {
                if (notesModel.isPresent()) {
                    if (notesModel.get().isTrash() == true) {
                        notesModel.get().setTrash(false);
                        noteRepository.save(notesModel.get());
                        return new Response("Restoring Note.", 200, notesModel.get());
                    }
                    throw new NoteNotFound(400, "Note is Note In Trash !");
                }
                throw new NoteNotFound(400, "Note not found !");
            }
            throw new NoteNotFound(400, "Note is not accessible !");
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to list all trash note.
     * @param token
     * @return
     */
    @Override
    public Response allTrashNote(String token) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
          List<NotesModel> trashList=noteRepository.findAll().stream().filter(x-> x.isTrash()==true && x.getUserId()==userId).collect(Collectors.toList());
            return new Response("Fetching All trash note",200,trashList);
        }
        throw new NoteNotFound(400,"User Not Found !");

    }

    /**
     * purpose-method to pin note.
     * @param token
     * @param noteId
     * @return
     */
    @Override
    public Response pinNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel=noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                if (notesModel.get().getUserId() == userId) {
                    if (notesModel.get().isArchive() == false) {
                        notesModel.get().setPin(true);
                        noteRepository.save(notesModel.get());
                        return new Response("Pined Note  ", 200, notesModel.get());
                    }
                    throw new NoteNotFound(400, "Note is archive, unarchive it first.");
                }
                throw  new NoteNotFound(400,"Note is not accessible!");
            }
            throw  new NoteNotFound(400,"Note Not Found !");
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to unpin note.
     * @param token
     * @param noteId
     * @return
     */

    @Override
    public Response unPinNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                if (notesModel.get().getUserId() == userId) {
                    if (notesModel.get().isPin() == true) {
                        notesModel.get().setPin(false);
                        noteRepository.save(notesModel.get());
                        return new Response("Unpin Note", 200, notesModel.get());
                    }
                    throw  new NoteNotFound(400,"Note is note pin !");
                }
                throw  new NoteNotFound(400,"Note is note accessible !");
            }
            throw  new NoteNotFound(400,"Note Not Found !");
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to list all pined note.
     * @param token
     * @return
     */

    @Override
    public Response allPinNote(String token) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            List<NotesModel> pinNoteList=noteRepository.findAll().stream().filter(x->x.getUserId()==userId && x.isPin()==true && x.isArchive()==false).collect(Collectors.toList());
            return new Response("Fetching All pin note",200,pinNoteList);
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to archive note.
     * @param token
     * @param noteId
     * @return
     */
    @Override
    public Response archiveNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel=noteRepository.findById(noteId);
            if (notesModel.isPresent()){
                if (notesModel.get().getUserId()==userId) {
                    if (notesModel.get().isPin() == false) {
                        notesModel.get().setArchive(true);
                        noteRepository.save(notesModel.get());
                        return new Response("Note moving into archive ", 200, notesModel.get());
                    }
                    throw new NoteNotFound(400, "Note is pined, unpin it first.");
                }
                throw  new NoteNotFound(400,"Note is not accessible !");
            }
            throw  new NoteNotFound(400,"Note Not Found !");
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to unarchive note.
     * @param token
     * @param noteId
     * @return
     */
    @Override
    public Response unArchiveNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                if (notesModel.get().getUserId() == userId) {
                    if (notesModel.get().isArchive() == true) {
                        notesModel.get().setArchive(false);
                        noteRepository.save(notesModel.get());
                        return new Response("unarchive note", 200, notesModel.get());
                    }
                    throw  new NoteNotFound(400,"Note is not archive !");
                }
                throw  new NoteNotFound(400,"Note is not accessible!");
            }
            throw  new NoteNotFound(400,"Note Not Found !");
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to list al archive notes.
     * @param token
     * @return
     */
    @Override
    public Response allArchiveNote(String token) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            List<NotesModel> archiveNoteList=noteRepository.findAll().stream().filter(x->x.getUserId()==userId && x.isPin()==false && x.isArchive()==true).collect(Collectors.toList());
            return new Response("Fetching All archive note",200,archiveNoteList);
        }
        throw new NoteNotFound(400,"User Not Found !");
    }

    /**
     * purpose-method to delete note from database.
     * @param token
     * @param noteId
     * @return
     */
    @Override
    public Response permanentlyDeleteNote(String token, long noteId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                if (notesModel.get().getUserId()==userId) {
                    if (notesModel.get().isTrash() == true) {
                        noteRepository.delete(notesModel.get());
                        return new Response("Permanently deleting note", 200, notesModel.get());
                    }
                    throw new NoteNotFound(400, "Note is not in trash !");
                }
                throw new NoteNotFound(400, "Note is not accessible !");
            }
            throw new NoteNotFound(400, "Note Not Found !");
        }
        throw new NoteNotFound(400, "User Not Found !");
    }

    /**
     * purpose-method to change colour of note.
     * @param token
     * @param noteId
     * @param colour
     * @return
     */
    @Override
    public Response changeColourOfNote(String token, long noteId,String colour) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                if (notesModel.get().getUserId() == userId) {
                    notesModel.get().setColour(colour);
                    noteRepository.save(notesModel.get());
                    return new Response("Changing colour of note to " + colour, 200, notesModel.get());
                }
                throw new NoteNotFound(400, "Note is not accessible !");
            }
            throw new NoteNotFound(400, "Note Not Found !");
        }
        throw new NoteNotFound(400, "User Not Found !");
    }

    /**
     * purpose-method to add label in note.
     * @param token
     * @param noteId
     * @param labelId
     * @return
     */
    @Override
    public Response addLabel(String token, long noteId, long labelId) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null ) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                if (notesModel.get().getUserId() == userId) {
                    Optional<LabelModel> labelModel = labelRepository.findById(labelId);
                    notesModel.get().getLabelList().add(labelModel.get());
                    noteRepository.save(notesModel.get());
                    return new Response("adding label", 200, notesModel.get());
                }
                throw new NoteNotFound(400, "Note is not accessible !");
            }
            throw new NoteNotFound(400, "Note Not Found !");
        }
        throw new NoteNotFound(400, "User Not Found !");
    }

    /**
     * purpose-method to add collaborators in note.
     * @param collbEmailId
     * @param noteId
     * @return
     */
    @Override
    public Response addCollaborator(String token, String collbEmailId, long noteId) {
            Long userId= tokenUtil.decodeToken(token);
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()){
                if (notesModel.get().getUserId()==userId) {
                    if (isCollaboratePresent(collbEmailId)!=null){

                        NotesModel newNote=new NotesModel();
                        newNote.setTittle(notesModel.get().getTittle());
                        newNote.setDescription(notesModel.get().getDescription());
                        newNote.setColour(notesModel.get().getColour());
                        newNote.setUserId(2);
                        newNote.setEmailId(collbEmailId);
                        notesModel.get().getCollaborator().add(collbEmailId);
                        noteRepository.save(newNote);
                        noteRepository.save(notesModel.get());
                        return new Response("adding collaborator", 200, notesModel.get());
                    }
                    throw new NoteNotFound(400, "Collaborator Not Found !");
                }
                throw new NoteNotFound(400, "Note is not accessible !");
            }
            throw new NoteNotFound(400, "Note Not Found !");
    }

    /**
     * purpose-method to set reminder in note.
     * @param token
     * @param noteId
     * @param date
     * @return
     */
    @Override
    public Response setReminder(String token, long noteId, LocalDate date) {
        Long userId= tokenUtil.decodeToken(token);
        if (isUserPresent(userId)!=null) {
            Optional<NotesModel> notesModel = noteRepository.findById(noteId);
            if (notesModel.isPresent()) {
                notesModel.get().setReminderTime(date);
                noteRepository.save(notesModel.get());
                return new Response("reminder set at "+date,200,notesModel.get());
            }
            throw new NoteNotFound(400, "Note Not Found !");
        }
        throw new NoteNotFound(400, "User Not Found !");
    }

    /**
     * purpose-method to check user present in database.
     * @param userId
     * @return
     */
    public Response isUserPresent(Long userId){
      return   restTemplate.getForObject("http://FUNDOO-USER-SERVICE/user/validatingUser/"+userId,Response.class);
    }

    /**
     * purpose-method to check user is active or note.
     * @param token
     * @return
     */
    public Boolean isUserActive(String token){
        return restTemplate.getForObject("http://FUNDOO-USER-SERVICE/user/activateUser/" + token, Boolean.class);
    }

    /**
     * purpose-method to check user email is present or note in database.
     * @param collbEmailId
     * @return
     */
    public Response isCollaboratePresent(String collbEmailId){
        return restTemplate.getForObject("http://FUNDOO-USER-SERVICE/user/emailVerify/" + collbEmailId, Response.class);
    }

}
