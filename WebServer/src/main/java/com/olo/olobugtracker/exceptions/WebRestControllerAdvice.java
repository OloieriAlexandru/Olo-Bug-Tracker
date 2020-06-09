package com.olo.olobugtracker.exceptions;

import com.olo.olobugtracker.dtos.GenericErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebRestControllerAdvice {
    @ExceptionHandler(value = GenericNotFoundException.class)
    public ResponseEntity<?> handleNotFoundExceptions(GenericNotFoundException e) {
        GenericErrorDTO errorDTO = new GenericErrorDTO(e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleFailedJwtAuthentification(UsernameNotFoundException e) {
        GenericErrorDTO errorDTO = new GenericErrorDTO(e.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = GenericBadRequestException.class)
    public ResponseEntity<?> handleDuplicateExceptions(GenericDuplicateException e) {
        GenericErrorDTO errorDTO = new GenericErrorDTO((e.getMessage()));
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
