package com.olo.olobugtracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BugGetAllProjectInfoDTO {
    private Long id;

    private String name;

    private List<BugGetAllDTO> bugs = new ArrayList<>();

    public BugGetAllProjectInfoDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addBug(BugGetAllDTO bug) {
        bugs.add(bug);
    }

    public List<BugGetAllDTO> getBugs() {
        return bugs;
    }
}
