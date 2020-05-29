package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "UserRole")
@Table(name = "users_roles")
public class UserRole {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 12)
    private UserRoleEnum role;

    public UserRole(UserRoleEnum role) {
        this.id = role.getValue();
        this.role = role;
    }
}
