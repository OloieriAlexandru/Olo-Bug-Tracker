package com.olo.olobugtracker.dtos;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class UserCreateDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
