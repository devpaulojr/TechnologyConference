package com.devpaulojr.technologyconference.controllers.exceptions;

public class BadRequestException extends IllegalArgumentException {
    public BadRequestException(String message) {
        super(message);
    }
}
