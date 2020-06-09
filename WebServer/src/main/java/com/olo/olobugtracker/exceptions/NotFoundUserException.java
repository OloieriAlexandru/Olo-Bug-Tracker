package com.olo.olobugtracker.exceptions;

public class NotFoundUserException extends GenericNotFoundException {
    public NotFoundUserException(Long userId) {
        super("An user with id \"" + userId + "\" doesn't exist!");
    }
}
