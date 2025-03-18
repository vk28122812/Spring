package io.datajek.spring;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@ControllerAdvice
public class EbookControllerAdviser {

    @ExceptionHandler
    public ResponseEntity<EbookErrorResponse> ebookNotFoundHandler(EbookNotFoundException ex, HttpServletRequest req){
        EbookErrorResponse error = new EbookErrorResponse(ZonedDateTime.now(), HttpStatus.NOT_FOUND.value(), req.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    };

    @ExceptionHandler
    public ResponseEntity<EbookErrorResponse> genericHandler(Exception ex, HttpServletRequest req){
        EbookErrorResponse error = new EbookErrorResponse(ZonedDateTime.now(), HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    };
}