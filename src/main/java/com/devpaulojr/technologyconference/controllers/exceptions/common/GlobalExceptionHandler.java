package com.devpaulojr.technologyconference.controllers.exceptions.common;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorInside;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.ErrorOut;
import com.devpaulojr.technologyconference.controllers.exceptions.dto.StandardError;
import com.devpaulojr.technologyconference.controllers.util.CustomErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler implements CustomErrorMessage {

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
    @ExceptionHandler(BadRequestException.class)
    public StandardError constraintViolationException(BadRequestException exception,
                                             HttpServletRequest path){

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String defaultMessage = "A requisição não pôde ser processada devido a dados inválidos. Corrija e tente " +
                "novamente.";

        Throwable rootCause = exception.initCause(exception.getCause());

        String detailsMessage = (rootCause != null) ? rootCause.getMessage() : defaultMessage;

        return new StandardError(
                timestamp,
                status.value(),
                path.getRequestURI(),
                defaultMessage,
                Collections.singletonList(detailsMessage)
        );

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public StandardError resourceNotFoundException(ResourceNotFoundException exception,
                                          HttpServletRequest path) {

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.NOT_FOUND;
        String defaultMessage = "O recurso solicitado não foi encontrado. Verifique a URL ou os parâmetros informados.";

        Throwable rootCause = exception.initCause(exception.getCause());

        String detailsMessage = (rootCause != null) ? rootCause.getMessage().substring(29, 64) : defaultMessage;

        return new StandardError(
                timestamp,
                status.value(),
                path.getRequestURI(),
                "Error: " + detailsMessage,
                Collections.singletonList(Objects.requireNonNull(rootCause).getMessage())
        );

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public StandardError dataIntegrityViolationException(DataIntegrityViolationException exception,
                                                     HttpServletRequest path){

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.CONFLICT;
        String defaultMessage = "O recurso não pôde ser processado devido a um conflito. Verifique os dados e tente " +
                "novamente.";

        Throwable rootCause = exception.getRootCause();
        String detailedMessage = (rootCause != null) ? rootCause.toString() : defaultMessage;

        String fieldError = errorMessageConflict(detailedMessage);
        String detailsFieldError = errorMessageDetailsConflict(detailedMessage);

        return new StandardError(
                timestamp,
                status.value(),
                path.getRequestURI(),
                "Erro de integridade de dados no campo: " + fieldError,
                Collections.singletonList(detailsFieldError)
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public StandardError accessDeniedException(AccessDeniedException exception,
                                           HttpServletRequest path){

        String defaultMessage = "Erro: Acesso sem permissão!!";
        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        String detailedMessage = (exception != null) ? exception.toString() : defaultMessage;

        String detailsFieldError = errorMessageDetailsConflict(detailedMessage);

        return new StandardError(
                timestamp,
                status.value(),
                path.getRequestURI(),
                defaultMessage,
                Collections.singletonList(detailsFieldError)
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public StandardError internalServerErrorException(RuntimeException exception,
                                                 HttpServletRequest path){

        Instant timestamp = Instant.now();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Ocorreu um erro inesperado no servidor. " +
                "Tente novamente mais tarde. Se o problema persistir, entre em contato com o suporte.";

        return new StandardError(
                timestamp,
                status.value(),
                path.getRequestURI(),
                message,
                List.of()
        );
    }
}
