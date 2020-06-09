package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.repositories.UserRepository;

import java.util.Optional;

public class BaseService {
    protected UserRepository userRepository;

    public BaseService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Returns an user from the repository
     *
     * @param userId The id of the user
     * @return The user identified by 'userId' id
     * @throws NotFoundUserException When the user doesn't exist in the repository
     */
    protected User getUserFromRepositoryById(Long userId) throws NotFoundUserException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new NotFoundUserException(userId);
        }

        return userOptional.get();
    }
}
