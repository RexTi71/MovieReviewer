package com.dominik.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseBuilder {
    public ResponseEntity<String> buildSuccessResponse(String message){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"response\": \"" + message + "\"}");
    }
    public ResponseEntity<String> buildErrorResponse(String message){
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"response\": \"" + message + "\"}");
    }

    public ResponseEntity<String> buildErrorResponse(String message, HttpStatus httpStatus){
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body("{\"response\": \"" + message + "\"}");
    }
}
