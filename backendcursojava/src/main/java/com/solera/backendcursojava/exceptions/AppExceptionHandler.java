package com.solera.backendcursojava.exceptions;

import com.solera.backendcursojava.models.reponses.ErrorMessage;
import com.solera.backendcursojava.models.reponses.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException (Exception exception, WebRequest webRequest) {
        LOGGER.info("handleException");
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { AuthenticationOwnException.class })
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationOwnException exception, WebRequest webRequest) {
        LOGGER.info("handleAuthenticationException");
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { EmailExistsException.class })
    public ResponseEntity<Object> handleEmailExistsException (EmailExistsException exception, WebRequest webRequest) {
        LOGGER.info("handleEmailExistsException");
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { PermissionsException.class })
    public ResponseEntity<Object> handlePermissionsException (PermissionsException exception, WebRequest webRequest) {
        LOGGER.info("handlePermissionsException");
        ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleEmailExistsException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        LOGGER.info("handleEmailExistsException");

        Map<String, String> errors = new HashMap<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }

        ValidationErrors validationErrors = new ValidationErrors(errors, new Date());

        return new ResponseEntity<>(validationErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
