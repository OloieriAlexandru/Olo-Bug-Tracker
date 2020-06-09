package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.UsersConnectionGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionInvitationGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionUserInfoDTO;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.exceptions.NotFoundUsersConnectionInvitationException;
import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.models.UsersConnection;
import com.olo.olobugtracker.models.UsersConnectionInvitation;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.repositories.UsersConnectionInvitationRepository;
import com.olo.olobugtracker.repositories.UsersConnectionRepository;
import com.olo.olobugtracker.services.abstractions.UsersConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersConnectionServiceImpl implements UsersConnectionService {
    private UsersConnectionInvitationRepository usersConnectionInvitationRepository;

    private UsersConnectionRepository usersConnectionRepository;

    private UserRepository userRepository;

    @Autowired
    public UsersConnectionServiceImpl(UsersConnectionInvitationRepository usersConnectionInvitationRepository,
                                      UsersConnectionRepository usersConnectionRepository,
                                      UserRepository userRepository) {
        this.usersConnectionInvitationRepository = usersConnectionInvitationRepository;
        this.usersConnectionRepository = usersConnectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersConnectionGetAllDTO> findAllConnections(Long userId) throws NotFoundUserException {
        User user = getUserFromRepositoryById(userId);

        List<UsersConnection> usersConnections = usersConnectionRepository.findAllByUser1OrUser2(user, user);
        List<UsersConnectionGetAllDTO> resultConnections = new ArrayList<>();

        for (UsersConnection usersConnection : usersConnections) {
            resultConnections.add(new UsersConnectionGetAllDTO(usersConnection.getId(), new UsersConnectionUserInfoDTO(usersConnection.getOtherUser(user))));
        }

        return resultConnections;
    }

    @Override
    public List<UsersConnectionInvitationGetAllDTO> findAllInvitations(Long userId) throws NotFoundUserException {
        User user = getUserFromRepositoryById(userId);

        List<UsersConnectionInvitation> usersConnectionInvitations = usersConnectionInvitationRepository.findAllByReceiver(user);
        List<UsersConnectionInvitationGetAllDTO> resultInvitations = new ArrayList<>();

        for (UsersConnectionInvitation usersConnectionInvitation : usersConnectionInvitations) {
            resultInvitations.add(new UsersConnectionInvitationGetAllDTO(usersConnectionInvitation.getId(), new UsersConnectionUserInfoDTO(usersConnectionInvitation.getSender())));
        }

        return resultInvitations;
    }

    @Override
    public void createInvitation(Long senderId, Long receiverId) throws NotFoundUserException {
        User sender = getUserFromRepositoryById(senderId);
        User receiver = getUserFromRepositoryById(receiverId);

        UsersConnectionInvitation invitation = new UsersConnectionInvitation(sender, receiver);
        usersConnectionInvitationRepository.save(invitation);
    }

    @Override
    @Transactional
    public void acceptInvitation(Long userThatAcceptsId, Long invitationId) throws GenericNotFoundException {
        User receiver = getUserFromRepositoryById(userThatAcceptsId);

        Optional<UsersConnectionInvitation> optInvitation = usersConnectionInvitationRepository.findById(invitationId);
        if (!optInvitation.isPresent()) {
            throw new NotFoundUsersConnectionInvitationException(invitationId);
        }

        UsersConnectionInvitation invitation = optInvitation.get();
        if (!invitation.getReceiver().equals(receiver)) {
            throw new GenericNotFoundException("The specified invitation doesn't exist!");
        }

        User sender = invitation.getSender();
        UsersConnection connection = new UsersConnection(receiver, sender);
        usersConnectionRepository.save(connection);

        usersConnectionInvitationRepository.delete(invitation);
    }

    private User getUserFromRepositoryById(Long userId) throws NotFoundUserException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new NotFoundUserException(userId);
        }

        return userOptional.get();
    }
}
