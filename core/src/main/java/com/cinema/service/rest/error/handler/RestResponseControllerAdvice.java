package com.cinema.service.rest.error.handler;

import com.amazonaws.AmazonServiceException;
import com.cinema.service.rest.error.ErrorResponse;
import com.cinema.exception.MovieImageNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(RestResponseControllerAdvice.class);

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getLocalizedMessage(),
                        null
                );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(value = {FileUploadException.class})
    protected ResponseEntity<Object> handleFileUploadException(FileUploadException ex, WebRequest request) {
        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "File upload failed",
                        List.of(ex.getLocalizedMessage())
                );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "validation error",
                        errors
                );
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieImageNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleMovieImageNotFound(MovieImageNotFoundException ex) {
        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Resource not found",
                        List.of(ex.getLocalizedMessage())
                );
        log.error("Movie image not found", ex);
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AmazonServiceException.class)
    protected ResponseEntity<ErrorResponse> handleAmazonException(AmazonServiceException ex) {
        String generalErrorMessage = "Amazon API returned an error";
        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        ex.getStatusCode(),
                        generalErrorMessage,
                        null
                );
        log.error(generalErrorMessage, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.valueOf(ex.getStatusCode()));
    }
}