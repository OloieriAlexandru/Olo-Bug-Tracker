package com.olo.olobugtracker.dtos;

import com.olo.olobugtracker.models.BugPriorityEnum;
import com.olo.olobugtracker.models.BugStatusEnum;
import com.olo.olobugtracker.models.BugUserRoleEnum;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@NoArgsConstructor
public class BugUpdateDTO {
    private Long id;

    private BugPriorityEnum priority;

    private BugUserRoleEnum userRole;

    private BugStatusEnum status;

    private String title;

    private String description;

    private Date dueDate;

    public Long getId() {
        return id;
    }

    public BugPriorityEnum getPriority() {
        return priority;
    }

    public BugStatusEnum getStatus() {
        return status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BugUserRoleEnum getUserRole() {
        return userRole;
    }
}
