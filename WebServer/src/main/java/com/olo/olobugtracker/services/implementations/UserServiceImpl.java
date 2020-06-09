package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserCredentialsDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;
import com.olo.olobugtracker.dtos.UserUpdateDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.services.abstractions.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder bCryptEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder bCryptEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptEncoder = bCryptEncoder;
    }

    @Override
    public Long getUserId(String username) throws GenericNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new GenericNotFoundException("An user with the username \"" + username + "\" doesn't exist!");
        }
        return user.getId();
    }

    @Override
    public UserCredentialsDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserCredentialsDTO.class);
    }

    /**
     * Creates a new user
     *
     * @param newUser The information of the newly created user
     * @return An user DTO
     * @throws GenericDuplicateException When a user with the same username already exists
     */
    @Override
    public UserGetByUsernameDTO create(UserCreateDTO newUser) throws GenericDuplicateException {
        User existentUser = userRepository.findByUsername(newUser.getUsername());
        if (existentUser != null) {
            throw new GenericDuplicateException("An user with the username \"" + newUser.getUsername() + "\" already exists!");
        }

        User user = modelMapper.map(newUser, User.class);
        user.setPassword(bCryptEncoder.encode(newUser.getPassword()));

        user = userRepository.save(user);

        return modelMapper.map(user, UserGetByUsernameDTO.class);
    }

    /**
     * Updates the information of the user identified by 'updatedUser.id' id
     *
     * @param updatedUser The updated user information
     * @throws NotFoundUserException When a user with the 'updatedUser.id' id doesn't exist
     */
    @Override
    public void update(UserUpdateDTO updatedUser) throws NotFoundUserException {
        Optional<User> optionalUser = userRepository.findById(updatedUser.getId());

        if (!optionalUser.isPresent()) {
            throw new NotFoundUserException(updatedUser.getId());
        }

        User user = optionalUser.get();
        modelMapper.map(updatedUser, user);
    }
}
