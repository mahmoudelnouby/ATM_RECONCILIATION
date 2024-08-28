package com.example.ATM_RECONCILIATION.exceptionHandler;


import com.example.ATM_RECONCILIATION.payload.response.Response;
import org.hibernate.PropertyValueException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> NotFoundException(ChangeSetPersister.NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new Response(404,ex.getMessage(),"","Not Found data"));
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeExceptionException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","Runtime Exception"));
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> PropertyReferenceException(PropertyReferenceException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","There is no exist for this field"));
    }
    
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","Invalid number format"));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","Invalid date/time format"));
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleJsonParseException(JsonParseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","Invalid JSON format"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","Illegal argument"));
    }
    
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> NullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","Null Pointer Exception" ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","There is error in Json format" ));
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<?> PropertyValueException(PropertyValueException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"","There is error in Json format"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String paramValue = Objects.requireNonNull(ex.getValue()).toString();
        String errorMessage = "Type mismatch for parameter '" + paramName + "'. Value '" + paramValue + "' is not of the expected type.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(500,ex.getMessage(),"",errorMessage));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> AuthorizationHandlerFn(AccessDeniedException ex) {
        String msg = ex.getMessage();
        String errorMessage = msg + ", Please contact the administrator.";
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new Response(403, errorMessage,"", msg));
    }
    
}