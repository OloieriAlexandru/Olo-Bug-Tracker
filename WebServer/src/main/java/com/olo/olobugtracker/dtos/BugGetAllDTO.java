package com.olo.olobugtracker.dtos;

import com.olo.olobugtracker.models.Bug;
import com.olo.olobugtracker.models.BugPriorityEnum;
import com.olo.olobugtracker.models.BugStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BugGetAllDTO {
    private Long id;

    private BugStatusEnum status;

    private BugPriorityEnum priority;

    private String title;

    private Date dueDate;

    public BugGetAllDTO(Bug bug) {
        this.id = bug.getId();
        this.title = bug.getTitle();
        this.dueDate = bug.getDueDate();
        this.status = bug.getStatus().getStatus();
        this.priority = bug.getPriority().getPriority();
    }
}
