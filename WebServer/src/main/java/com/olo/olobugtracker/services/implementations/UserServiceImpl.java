package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserCredentialsDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;
import com.olo.olobugtracker.models.User;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.services.abstractions.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
