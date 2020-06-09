package com.olo.olobugtracker.exceptions;

public class NotFoundBugException extends GenericNotFoundException {
    public NotFoundBugException(Long bugId) {
        super("A bug with id \"" + bugId + "\" doesn't exist!");
    }
}
