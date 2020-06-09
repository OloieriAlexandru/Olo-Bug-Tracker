package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateDTO {
    private String name;

    private String description;

    public ProjectCreateDTO() {

    }

    public ProjectCreateDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
