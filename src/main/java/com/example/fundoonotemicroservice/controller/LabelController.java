package com.example.fundoonotemicroservice.controller;

import com.example.fundoonotemicroservice.dto.LabelDTO;
import com.example.fundoonotemicroservice.service.LabelService;
import com.example.fundoonotemicroservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * purpose-label operation api's.
 * @author anuj solanki
 * @date 14/09/2022
 * @version 1.0
 */

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    LabelService labelService;

    /**
     * purpose-api for crating label.
     * @param token
     * @param noteId
     * @param labelDTO
     * @return label details.
     */
    @PostMapping("/creatLabel")
    public ResponseEntity<Response> addLAbel(@RequestParam String token,@RequestParam long noteId,@Valid @RequestBody LabelDTO labelDTO) {
        Response response = labelService.creatLabel(token,noteId,labelDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to fetch label.
     * @param token
     * @param labelId
     * @return label details.
     */
    @GetMapping("/getLabel")
    public ResponseEntity<Response> readLabel(@RequestHeader String token, @RequestParam long labelId) {
        Response response = labelService.readLabel(token,labelId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api for updating label.
     * @param token
     * @param labelId
     * @param labelDTO
     * @return updated label.
     */
    @PutMapping("/updateLabel")
    public ResponseEntity<Response> updateLabel(@RequestHeader String token, @RequestParam long labelId,@RequestBody LabelDTO labelDTO) {
        Response response = labelService.updateLabel(token,labelId,labelDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * purpose-api to delete label.
     * @param token
     * @param labelId
     * @return deleted label
     */
    @DeleteMapping("/deleteLabel")
    public ResponseEntity<Response> deleteLabel(@RequestHeader String token, @RequestParam long labelId) {
        Response response = labelService.deleteLabel(token,labelId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
