package com.olo.olobugtracker.dtos;

import com.olo.olobugtracker.models.BugPriorityEnum;
import com.olo.olobugtracker.models.BugStatusEnum;
import com.olo.olobugtracker.models.BugUserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BugGetByIdDTO {
    private Long id;

    private BugStatusEnum status;

    private BugPriorityEnum priority;

    private BugUserRoleEnum userRole;

    private String title;

    private String description;

    private Date dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(BugStatusEnum status) {
        this.status = status;
    }
}
