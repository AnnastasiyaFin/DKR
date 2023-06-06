package com.example.dkrproject.exception;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private List<String> errors;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }


    public ErrorDetails(Date timestamp, String message, String details, List<String> errors) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.errors = errors;
    }

}
