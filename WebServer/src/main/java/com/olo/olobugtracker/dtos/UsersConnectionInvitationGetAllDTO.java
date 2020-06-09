package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersConnectionInvitationGetAllDTO {
    private Long id;

    private UsersConnectionUserInfoDTO userInfo;

    public UsersConnectionInvitationGetAllDTO(Long id, UsersConnectionUserInfoDTO userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }
}
