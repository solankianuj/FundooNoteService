package com.example.fundoonotemicroservice.controller;

import com.example.fundoonotemicroservice.dto.FundooNoteDTO;
import com.example.fundoonotemicroservice.dto.LabelDTO;
import com.example.fundoonotemicroservice.model.NotesModel;
import com.example.fundoonotemicroservice.service.LabelService;
import com.example.fundoonotemicroservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    LabelService labelService;

    @PostMapping("/creatLabel")
    public ResponseEntity<Response> addLAbel(@RequestParam String token, @Valid @RequestBody LabelDTO labelDTO) {
        Response response = labelService.creatLabel(token,labelDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getLabel")
    public ResponseEntity<Response> readLabel(@RequestParam String token, @RequestParam long noteId,@RequestParam long labelId) {
        Response response = labelService.readLabel(token,noteId,labelId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
