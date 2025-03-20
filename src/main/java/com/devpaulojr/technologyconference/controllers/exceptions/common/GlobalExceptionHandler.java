package com.devpaulojr.technologyconference.controllers.exceptions.common;

import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorInside;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorOut;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
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
        String message = "A requisição foi processada, mas contém erros de validação. Verifique os dados enviados.";

        return new ErrorOut(
                timestamp,
                status.value(),
                path.getRequestURI(),
                message,
                errorInsides
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorOut constraintViolationException(ConstraintViolationException exception,
                                             HttpServletRequest path){

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "A requisição não pôde ser processada devido a dados inválidos. Corrija e tente novamente.";

        return new ErrorOut(
               timestamp,
               status.value(),
               path.getRequestURI(),
               message,
               List.of()
        );

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorOut resourceNotFoundException(ResourceNotFoundException exception,
                                          HttpServletRequest path) {

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "O recurso solicitado não foi encontrado. Verifique a URL ou os parâmetros informados.";

        return new ErrorOut(
                timestamp,
                status.value(),
                path.getRequestURI(),
                message,
                List.of()
        );

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorOut dataIntegrityViolationException(DataIntegrityViolationException exception,
                                                HttpServletRequest path){

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.CONFLICT;
        String message = "O recurso não pôde ser processado devido a um conflito. Verifique os dados e tente novamente.";

        return new ErrorOut(
                timestamp,
                status.value(),
                path.getRequestURI(),
                message,
                List.of()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorOut internalServerErrorException(RuntimeException exception,
                                                 HttpServletRequest path){

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Ocorreu um erro inesperado no servidor. " +
                "Tente novamente mais tarde. Se o problema persistir, entre em contato com o suporte.";

        return new ErrorOut(
                timestamp,
                status.value(),
                path.getRequestURI(),
                message,
                List.of()
        );
    }
}
