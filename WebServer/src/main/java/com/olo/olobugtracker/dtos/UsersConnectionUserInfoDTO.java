package com.olo.olobugtracker.dtos;

import com.olo.olobugtracker.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersConnectionUserInfoDTO {
    private Long id;

    private String firstName;

    private String lastName;

    public UsersConnectionUserInfoDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
