package com.cinema.core.exception.handler;

import com.amazonaws.AmazonServiceException;
import com.cinema.core.exception.MovieImageNotFoundException;
import com.cinema.entrypoints.api.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

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
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The request was processed, but one or more fields contain invalid values.");
        problemDetail.setTitle("Validation error");
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        List<Map<String, String>> errorDetails = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String pointer = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("pointer", pointer);
            errorDetail.put("error", errorMessage);
            errorDetails.add(errorDetail);
        }

        problemDetail.setProperty("errors", errorDetails);
        return problemDetail;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleInvalidInputException(ConstraintViolationException e, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "the request was processed, but one or more fields contain invalid values");
        problemDetail.setTitle("Validation error");
        problemDetail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        List<Map<String, String>> errorDetails = new ArrayList<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String pointer = getPointer(violation.getPropertyPath());
            String errorMessage = violation.getMessage();

            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("pointer", pointer);
            errorDetail.put("error", errorMessage);

            errorDetails.add(errorDetail);
        }

        problemDetail.setProperty("errors", errorDetails);
        return problemDetail;
    }

    private String getPointer(Path propertyPath) {
        Iterator<Path.Node> iterator = propertyPath.iterator();
        String lastPart = null;

        while (iterator.hasNext()) {
            lastPart = iterator.next().getName();
        }

        return lastPart != null ? lastPart : "";
    }

}