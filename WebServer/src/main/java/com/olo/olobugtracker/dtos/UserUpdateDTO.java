package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    public Long getId() {
        return id;
    }
}
