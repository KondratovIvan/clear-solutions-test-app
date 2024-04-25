package com.example.clearsolutiontestapp.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

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
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Human you entered is too old"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnderageException.class)
    public ResponseEntity<?> underageException(){
        return new ResponseEntity<>(new MyGlobalExceptionHandler("Only users over 18 years of age can be registered"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception) {
        Map<String, Object> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Data
    private class MyGlobalExceptionHandler {
        private String message;

        public MyGlobalExceptionHandler(String message) {
            this.message = message;
        }
    }
}
