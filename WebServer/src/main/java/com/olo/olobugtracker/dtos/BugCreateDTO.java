package com.olo.olobugtracker.dtos;

import com.olo.olobugtracker.models.BugPriorityEnum;
import com.olo.olobugtracker.models.BugUserRoleEnum;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@NoArgsConstructor
public class BugCreateDTO {
    private BugPriorityEnum priority;

    private BugUserRoleEnum userRole;

    private String title;

    private String description;

    private Date dueDate;

    public BugPriorityEnum getPriority() {
        return priority;
    }

    public BugUserRoleEnum getUserRole() {
        return userRole;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getTitle() {
        return title;
    }
}
