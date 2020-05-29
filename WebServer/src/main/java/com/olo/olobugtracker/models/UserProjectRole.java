package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "UserProjectRole")
@Table(name = "users_projects_roles")
public class UserProjectRole {
    @EmbeddedId
    private UserProjectId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    private UserProjectRole() {
    }

    public UserProjectRole(User user, Project project, UserRole userRole) {
        this.id = new UserProjectId(user.getId(), project.getId());
        this.user = user;
        this.project = project;
        this.role = userRole;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProjectRole that = (UserProjectRole) o;
        return user.equals(that.user) &&
                project.equals(that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, project);
    }
}
