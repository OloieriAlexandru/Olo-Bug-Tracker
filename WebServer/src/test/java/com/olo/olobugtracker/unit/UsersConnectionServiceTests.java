package com.olo.olobugtracker.unit;

import com.olo.olobugtracker.exceptions.GenericBadRequestException;
import com.olo.olobugtracker.exceptions.NotFoundUserException;
import com.olo.olobugtracker.repositories.UserRepository;
import com.olo.olobugtracker.repositories.UsersConnectionInvitationRepository;
import com.olo.olobugtracker.repositories.UsersConnectionRepository;
import com.olo.olobugtracker.services.abstractions.UsersConnectionService;
import com.olo.olobugtracker.services.implementations.UsersConnectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UsersConnectionServiceTests {

    UserRepository userRepository;

    UsersConnectionRepository usersConnectionRepository;

    UsersConnectionInvitationRepository usersConnectionInvitationRepository;

    UsersConnectionService usersConnectionService;

    @BeforeEach
    public void mockInterfaces() {
        this.userRepository = mock(UserRepository.class);
        this.usersConnectionRepository = mock(UsersConnectionRepository.class);
        this.usersConnectionInvitationRepository = mock(UsersConnectionInvitationRepository.class);
        this.usersConnectionService = new UsersConnectionServiceImpl(usersConnectionInvitationRepository, usersConnectionRepository, userRepository);
    }

    @Test
    public void getConnectionsSuggestions_ThrowsException_IfLimitParameterIsInvalid() {
        Assertions.assertThrows(GenericBadRequestException.class, () -> {
            usersConnectionService.getConnectionsSuggestions(1L, -1L);
        });
    }

    @Test
    public void getConnectionsSuggestions_ThrowsException_IfTheUsersDoesNotExist() {
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundUserException.class, () -> {
            usersConnectionService.getConnectionsSuggestions(10L, 1L);
        });
    }
}
