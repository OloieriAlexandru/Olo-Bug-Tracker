package com.olo.olobugtracker.dtos;

import com.olo.olobugtracker.models.UserRoleEnum;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class UserRoleCreateDTO {
    Long userId;

    UserRoleEnum role;

    public Long getUserId() {
        return userId;
    }

    public UserRoleEnum getRole() {
        return role;
    }
}
