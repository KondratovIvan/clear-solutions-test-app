package com.example.clearsolutiontestapp.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CopyDataException.class)
    public ResponseEntity<?> copyDataException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("The user with this email already exist"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIsNotExistException.class)
    public ResponseEntity<?> userIsNotExistException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("The user is not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnrealAgeException.class)
    public ResponseEntity<?> unrealAgeException(){
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Human you entered is too old or too young"), HttpStatus.BAD_REQUEST);
    }

    @Data
    private class MyGlobalExceptionHandler {
        private String message;

        public MyGlobalExceptionHandler(String message) {
            this.message = message;
        }
    }
}
