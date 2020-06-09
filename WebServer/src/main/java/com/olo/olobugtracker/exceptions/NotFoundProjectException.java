package com.olo.olobugtracker.exceptions;

public class NotFoundProjectException extends GenericNotFoundException {
    public NotFoundProjectException(Long projectId) {
        super("A project with id \"" + projectId + "\" doesn't exist!");
    }
}
