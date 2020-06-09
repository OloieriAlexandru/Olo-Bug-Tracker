package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectGetAllDTO {
    private Long id;

    private String name;

    private String shortDescription;

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
