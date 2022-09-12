package com.example.fundoonotemicroservice.controller;

import com.example.fundoonotemicroservice.dto.FundooNoteDTO;
import com.example.fundoonotemicroservice.service.NoteService;
import com.example.fundoonotemicroservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping("/createNote")
    public ResponseEntity<Response> addNote(@Valid @RequestBody FundooNoteDTO noteDTO) {
        Response response = noteService.creatNote(noteDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/readNote")
    public ResponseEntity<Response> readNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.readNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/allNote")
    public ResponseEntity<Response> allNoteList(@RequestHeader String token) {
        Response response = noteService.readAllNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/updateNote")
    public ResponseEntity<Response> updateNote(@RequestHeader String token,@RequestParam long noteId,@Valid @RequestBody FundooNoteDTO fundooNoteDTO) {
        Response response = noteService.updateNote(token, noteId, fundooNoteDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/trashNote")
    public ResponseEntity<Response> trashNote(@RequestHeader String token,@RequestParam long noteId) {
        Response response = noteService.trashNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/allTrashNote")
    public ResponseEntity<Response> allTrashNote(@RequestHeader String token) {
        Response response = noteService.allTrashNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/pinNote")
    public ResponseEntity<Response> pinNote(@RequestHeader String token,long noteId) {
        Response response = noteService.pinNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/unPinNote")
    public ResponseEntity<Response> unPinNote(@RequestHeader String token,long noteId) {
        Response response = noteService.unPinNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/allPinedNote")
    public ResponseEntity<Response> allPinNote(@RequestHeader String token) {
        Response response = noteService.allPinNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/archiveNote")
    public ResponseEntity<Response> archiveNote(@RequestHeader String token,long noteId) {
        Response response = noteService.archiveNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/unArchiveNote")
    public ResponseEntity<Response> unArchiveNote(@RequestHeader String token,long noteId) {
        Response response = noteService.unArchiveNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/allArchiveNote")
    public ResponseEntity<Response> allArchiveNote(@RequestHeader String token) {
        Response response = noteService.allArchiveNote(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/restoreNote")
    public ResponseEntity<Response> restoreNote(@RequestHeader String token,long noteId) {
        Response response = noteService.restoreNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/permanentlyDeleteNote")
    public ResponseEntity<Response> permanentlyDeleteNote(@RequestHeader String token,long noteId) {
        Response response = noteService.permanentlyDeleteNote(token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/changeColour")
    public ResponseEntity<Response> changingColourOfNote(@RequestHeader String token,long noteId,String noteColour) {
        Response response = noteService.changeColourOfNote(token, noteId, noteColour);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
