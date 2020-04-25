package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectGetByIdDTO {
    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }
}
