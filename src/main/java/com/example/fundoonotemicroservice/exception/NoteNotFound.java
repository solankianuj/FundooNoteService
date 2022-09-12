package com.example.fundoonotemicroservice.exception;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NoteNotFound extends RuntimeException{
    private long errorCode;
    private String statusMessage;

    public NoteNotFound(long errorCode, String statusMessage) {
        super(statusMessage);
        this.errorCode = errorCode;
        this.statusMessage = statusMessage;
    }
}
