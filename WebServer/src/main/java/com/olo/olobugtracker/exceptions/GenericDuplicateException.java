package com.olo.olobugtracker.exceptions;

public class GenericDuplicateException extends GenericBadRequestException {
    public GenericDuplicateException(String message) {
        super(message);
    }
}
