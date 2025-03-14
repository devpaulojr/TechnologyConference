package com.devpaulojr.technologyconference.controllers.exceptions;

import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorInside;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorOut;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorOut unprocessableEntityException(MethodArgumentNotValidException exception,
                                             HttpServletRequest path){

        List<FieldError> errors = exception.getFieldErrors();

        List<ErrorInside> errorInsides = errors
                .stream()
                .map(error -> new ErrorInside(error.getField(), error.getDefaultMessage()))
                .toList();

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        return new ErrorOut(
                timestamp,
                status.value(),
                path.getRequestURI(),
                errorInsides
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorOut ConstraintViolationException(ConstraintViolationException exception,
                                             HttpServletRequest path){


        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ErrorOut(
               timestamp,
               status.value(),
               path.getRequestURI(),
               List.of()
        );

    }
}
