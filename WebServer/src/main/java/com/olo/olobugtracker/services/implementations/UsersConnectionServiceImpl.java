package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.UsersConnectionGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionInvitationGetAllDTO;
import com.olo.olobugtracker.dtos.UsersConnectionUserInfoDTO;
import com.olo.olobugtracker.exceptions.GenericBadRequestException;
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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UsersConnectionServiceImpl extends BaseService implements UsersConnectionService {
    private UsersConnectionInvitationRepository usersConnectionInvitationRepository;

    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    public UsersConnectionServiceImpl(UsersConnectionInvitationRepository usersConnectionInvitationRepository,
                                      UsersConnectionRepository usersConnectionRepository,
                                      UserRepository userRepository) {
        super(userRepository);
        this.usersConnectionInvitationRepository = usersConnectionInvitationRepository;
        this.usersConnectionRepository = usersConnectionRepository;
    }

    /**
     * Returns all the connections of the user identified by 'userId'
     *
     * @param userId The id of the user for whom the connections will be returned
     * @return A list of all the connections
     * @throws NotFoundUserException When there is no user with the specified id
     */
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

    /**
     * Returns all the connection invitations of the user identified by 'usedId'
     *
     * @param userId The id of the user for whom the invitations will be returned
     * @return A list of all the invitations that the used has not accepted yet
     * @throws NotFoundUserException When there is no user with the specified id
     */
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

    /**
     * Returns a list of connection suggestions for the user identified by 'userId' (not more than 'limit' suggestions)
     *
     * @param userId The id of the user for whom the connection suggestions will be returned
     * @param limit  The maximum number of suggestions that will be returned
     * @return A list of connection suggestions
     * @throws NotFoundUserException      When there is no user with the specified id
     * @throws GenericBadRequestException When the 'limit' parameter is invalid
     */
    @Override
    public List<UsersConnectionUserInfoDTO> getConnectionsSuggestions(Long userId, Long limit) throws NotFoundUserException, GenericBadRequestException {
        if (limit <= 0) {
            throw new GenericBadRequestException("Request parameter \"limit\" is invalid!");
        }

        User user = getUserFromRepositoryById(userId);
        List<User> connections = getUserConnectionsUsers(user);
        List<User> suggestions = computeUserConnectionsSuggestions(connections, user, limit);
        List<UsersConnectionUserInfoDTO> suggestedUsersResult = new ArrayList<>();

        for (User suggestedUser : suggestions) {
            suggestedUsersResult.add(new UsersConnectionUserInfoDTO(suggestedUser));
        }

        return suggestedUsersResult;
    }

    /**
     * Returns a list of maximum 'limit' connection suggestions for 'user' user
     *
     * @param connections The connections of the 'user' user
     * @param user        The user for whom the suggestions are computed
     * @param limit       The maximum number of suggestions that will be returned
     * @return A list of connection suggestions
     */
    private List<User> computeUserConnectionsSuggestions(List<User> connections, User user, Long limit) {
        Map<User, Integer> commonFriendsCountMap = getSuggestionsFrequencyMap(connections, user);
        List<User> result = new ArrayList<>();

        List<Pair<Integer, User>> frequenciesArray = new ArrayList<>();
        for (Map.Entry<User, Integer> entry : commonFriendsCountMap.entrySet()) {
            frequenciesArray.add(Pair.of(entry.getValue(), entry.getKey()));
        }
        frequenciesArray.sort((o1, o2) -> -Integer.compare(o1.getFirst(), o2.getFirst()));

        return result;
    }

    /**
     * Returns a map that assigns to the list of suggestions candidates a score
     *
     * @param connections The connections of the 'user' user
     * @param user        The user for whom the suggestions are computed
     * @return A score map
     */
    private Map<User, Integer> getSuggestionsFrequencyMap(List<User> connections, User user) {
        Map<User, Integer> commonFriendsCountMap = new HashMap<>();

        Set<User> alreadyConnectedSet = new HashSet<>(connections);
        alreadyConnectedSet.add(user);

        for (User connection : connections) {
            List<User> connectionConnections = getUserConnectionsUsers(connection);
            for (User potentialSuggestion : connectionConnections) {
                if (alreadyConnectedSet.contains(potentialSuggestion)) {
                    continue;
                }

                if (commonFriendsCountMap.containsKey(potentialSuggestion)) {
                    commonFriendsCountMap.put(potentialSuggestion, commonFriendsCountMap.get(potentialSuggestion) + 1);
                } else {
                    commonFriendsCountMap.put(potentialSuggestion, 1);
                }
            }
        }

        return commonFriendsCountMap;
    }

    /**
     * Returns all the connections of the 'user' user
     *
     * @param user The user for whom the connections are returned
     * @return A list of users, the connection of the 'user' user
     */
    private List<User> getUserConnectionsUsers(User user) {
        List<UsersConnection> connections = usersConnectionRepository.findAllByUser1OrUser2(user, user);
        List<User> connectionsUsers = new ArrayList<>();

        for (UsersConnection connection : connections) {
            connectionsUsers.add(connection.getOtherUser(user));
        }

        return connectionsUsers;
    }

    /**
     * Called when the user identified by 'senderId' sends a connection invitation to user identified by 'receiverId'
     *
     * @param senderId   The id of the user that sends the connection invitation
     * @param receiverId The id of the user that receives the connection invitation
     * @throws NotFoundUserException One of the two ids are invalid
     */
    @Override
    public void createInvitation(Long senderId, Long receiverId) throws NotFoundUserException {
        User sender = getUserFromRepositoryById(senderId);
        User receiver = getUserFromRepositoryById(receiverId);

        UsersConnectionInvitation invitation = new UsersConnectionInvitation(sender, receiver);
        usersConnectionInvitationRepository.save(invitation);
    }

    /**
     * Called when the user with id 'userThatAcceptsId' accepts the invitation with id 'invitationId'
     *
     * @param userThatAcceptsId The id of the user that wants to accept the invitation
     * @param invitationId      The id of the invitation
     * @throws GenericNotFoundException The user identified by the specified id doesn't have an invitation with 'invitationId' id
     */
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
}
