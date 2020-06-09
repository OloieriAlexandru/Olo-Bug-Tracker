package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersConnectionGetAllDTO {
    private Long id;

    private UsersConnectionUserInfoDTO userInfo;

    public UsersConnectionGetAllDTO(Long id, UsersConnectionUserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }
}
