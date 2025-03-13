package com.devpaulojr.technologyconference.controllers.exceptions;

import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorInside;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorOut;
import jakarta.servlet.http.HttpServletRequest;
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


        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        Instant timestamp = Instant.now();

        return new ErrorOut(
                timestamp,
                status.value(),
                path.getRequestURI(),
                errorInsides
        );
    }
}
