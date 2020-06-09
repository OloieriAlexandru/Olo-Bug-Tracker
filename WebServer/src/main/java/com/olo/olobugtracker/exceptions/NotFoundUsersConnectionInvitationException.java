package com.olo.olobugtracker.exceptions;

public class NotFoundUsersConnectionInvitationException extends GenericNotFoundException {
    public NotFoundUsersConnectionInvitationException(Long invitationId) {
        super("An invitation with id \"" + invitationId + "\" doesn't exist!");
    }
}
