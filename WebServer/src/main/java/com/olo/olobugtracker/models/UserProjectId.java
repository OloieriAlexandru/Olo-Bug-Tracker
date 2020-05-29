package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserProjectId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    private UserProjectId() {
    }

    public UserProjectId(Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProjectId that = (UserProjectId) o;
        return userId.equals(that.userId) &&
                projectId.equals(that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, projectId);
    }
}
