package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserCredentialsDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;

public interface UserService {
    UserCredentialsDTO findByUsername(String username);

    UserGetByUsernameDTO create(UserCreateDTO newUser);
}
