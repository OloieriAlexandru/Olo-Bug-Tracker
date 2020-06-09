package com.olo.olobugtracker.services.implementations;

import com.olo.olobugtracker.dtos.UserCredentialsDTO;
import com.olo.olobugtracker.services.abstractions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    /**
     * A method used for the JWT authentification
     *
     * @param username The username of the user that has to be authenticated
     * @return A UserDetails object
     * @throws UsernameNotFoundException When there is no user with the specified username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialsDTO userCredentials = userService.findByUsername(username);

        if (userCredentials == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new User(userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>());
    }
}
