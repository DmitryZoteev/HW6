package com.netcracker.exception;

import com.netcracker.responce.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<ErrorResponse>(new ErrorResponse("Incorrect request", details), HttpStatus.NOT_FOUND);
    }
}
