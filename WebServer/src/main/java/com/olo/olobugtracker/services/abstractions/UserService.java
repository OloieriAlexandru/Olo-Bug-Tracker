package com.olo.olobugtracker.services.abstractions;

import com.olo.olobugtracker.dtos.UserCreateDTO;
import com.olo.olobugtracker.dtos.UserCredentialsDTO;
import com.olo.olobugtracker.dtos.UserGetByUsernameDTO;
import com.olo.olobugtracker.exceptions.GenericDuplicateException;
import com.olo.olobugtracker.exceptions.GenericNotFoundException;

public interface UserService {
    Long getUserId(String username) throws GenericNotFoundException;

    UserCredentialsDTO findByUsername(String username);

    UserGetByUsernameDTO create(UserCreateDTO newUser) throws GenericDuplicateException;
}
