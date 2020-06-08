package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "BugUserRole")
@Table(name = "bug_user_roles")
public class BugUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 12)
    private BugUserRoleEnum userRole;

    public BugUserRole(BugUserRoleEnum userRole) {
        this.id = userRole.getValue();
        this.userRole = userRole;
    }
}
