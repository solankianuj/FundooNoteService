package com.example.fundoonotemicroservice.controller;

import com.example.fundoonotemicroservice.dto.FundooNoteDTO;
import com.example.fundoonotemicroservice.service.NoteService;
import com.example.fundoonotemicroservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * purpose-note operation api's.
 * @author anuj solanki
 * @date 14/09/2022
 * @version 1.0
 */

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    /**
     * purpose-api for creating note.
     * @param noteDTO
     * @return note details.
     */
    @PostMapping("/createNote")
    public ResponseEntity<Response> addNote(@RequestHeader String token,@Valid @RequestBody FundooNoteDTO noteDTO) {
        Response response = noteService.creatNote(token, noteDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to read note.
     * @param token
     * @param noteId
     * @return note .
     */
    @GetMapping("/readNote")
    public ResponseEntity<Response> readNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.readNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to fetch all note
     * @param token
     * @return list of note.
     */
    @GetMapping("/allNote")
    public ResponseEntity<Response> allNoteList(@RequestHeader String token) {
        Response response = noteService.readAllNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api for updating note details.
     * @param token
     * @param noteId
     * @param fundooNoteDTO
     * @return updated note.
     */
    @PutMapping("/updateNote")
    public ResponseEntity<Response> updateNote(@RequestHeader String token,@RequestParam long noteId,@Valid @RequestBody FundooNoteDTO fundooNoteDTO) {
        Response response = noteService.updateNote(token, noteId, fundooNoteDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to move note in trash.
     * @param token
     * @param noteId
     * @return trash message.
     */
    @GetMapping("/trashNote")
    public ResponseEntity<Response> trashNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.trashNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to fetch all trash notes.
     * @param token
     * @return list of trash notes.
     */
    @GetMapping("/allTrashNote")
    public ResponseEntity<Response> allTrashNote(@RequestHeader String token) {
        Response response = noteService.allTrashNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to pin note.
     * @param token
     * @param noteId
     * @return pin message.
     */
    @GetMapping("/pinNote")
    public ResponseEntity<Response> pinNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.pinNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to unpin note.
     * @param token
     * @param noteId
     * @return unpin message.
     */
    @GetMapping("/unPinNote")
    public ResponseEntity<Response> unPinNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.unPinNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to fetch all pin note.
     * @param token
     * @return list of pin note.
     */
    @GetMapping("/allPinedNote")
    public ResponseEntity<Response> allPinNote(@RequestHeader String token) {
        Response response = noteService.allPinNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to move note in archive.
     * @param token
     * @param noteId
     * @return archive message.
     */
    @GetMapping("/archiveNote")
    public ResponseEntity<Response> archiveNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.archiveNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to unarchive note.
     * @param token
     * @param noteId
     * @return unarchive message.
     */
    @GetMapping("/unArchiveNote")
    public ResponseEntity<Response> unArchiveNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.unArchiveNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to fetch all archive note.
     * @param token
     * @return list of archive note.
     */
    @GetMapping("/allArchiveNote")
    public ResponseEntity<Response> allArchiveNote(@RequestHeader String token) {
        Response response = noteService.allArchiveNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to restore note from trash.
     * @param token
     * @param noteId
     * @return restore note.
     */
    @GetMapping("/restoreNote")
    public ResponseEntity<Response> restoreNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.restoreNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to delete note from database.
     * @param token
     * @param noteId
     * @return delete message.
     */
    @DeleteMapping("/permanentlyDeleteNote")
    public ResponseEntity<Response> permanentlyDeleteNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.permanentlyDeleteNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to change colour of note.
     * @param token
     * @param noteId
     * @param noteColour
     * @return
     */
    @PutMapping("/changeColour")
    public ResponseEntity<Response> changingColourOfNote(@RequestHeader String token,@RequestParam long noteId,@RequestParam String noteColour) {
        Response response = noteService.changeColourOfNote(token, noteId, noteColour);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to add label with note.
     * @param token
     * @param noteId
     * @param labelId
     * @return note.
     */
    @PutMapping("/addLabel")
    public ResponseEntity<Response> addingLabel(@RequestHeader String token,@RequestParam long noteId,@RequestParam long labelId) {
        Response response = noteService.addLabel(token, noteId, labelId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to add collaborators in note.
     * @param collabEmailId
     * @param noteId
     * @return note details.
     */
    @PutMapping("/addCollaborator")
    public ResponseEntity<Response> addCollaborator(@RequestHeader String token,@RequestParam String collabEmailId,@RequestParam long noteId) {
        Response response = noteService.addCollaborator(token,collabEmailId,noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to set reminder for user.
     * @param token
     * @param noteId
     * @param date
     * @return reminding mail.
     */
    @PostMapping("/setReminder")
    public ResponseEntity<Response> setReminder (@RequestHeader String token, @RequestParam long noteId, @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Response response = noteService.setReminder(token, noteId, date);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
