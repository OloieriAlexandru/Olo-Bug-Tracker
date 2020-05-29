package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ProjectGetByIdDTO {
    private Long id;
    private String name;
    private String description;

    public ProjectGetByIdDTO() {
    }

    public ProjectGetByIdDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectGetByIdDTO that = (ProjectGetByIdDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
