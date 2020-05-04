package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public String getPassword() {
        return password;
    }
}
