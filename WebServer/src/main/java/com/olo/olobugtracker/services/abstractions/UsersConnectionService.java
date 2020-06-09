package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.UsersConnectionGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionInvitationGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionUserInfoDTO;
import com.olo.olobugtracker.exceptions.GenericBadRequestException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;

import java.util.List;

public interface UsersConnectionService {
    List<UsersConnectionGetAllDTO> findAllConnections(Long userId) throws NotFoundUserException;

    List<UsersConnectionInvitationGetAllDTO> findAllInvitations(Long userId) throws NotFoundUserException;

    List<UsersConnectionUserInfoDTO> getConnectionsSuggestions(Long userId, Long limit) throws NotFoundUserException, GenericBadRequestException;

    void createInvitation(Long senderId, Long receiverId) throws NotFoundUserException;

    void acceptInvitation(Long userThatAcceptsId, Long invitationId) throws GenericNotFoundException, GenericBadRequestException;
}
