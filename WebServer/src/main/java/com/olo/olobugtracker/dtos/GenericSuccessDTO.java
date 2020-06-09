package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericSuccessDTO {
    private String message;

    public GenericSuccessDTO(String message) {
        this.message = message;
    }
}
