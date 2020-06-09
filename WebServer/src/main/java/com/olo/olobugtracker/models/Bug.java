package com.olo.olobugtracker.models;

import com.olo.olobugtracker.dtos.BugCreateDTO;
import com.olo.olobugtracker.dtos.BugUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "Bug")
@Table(name = "bugs")
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_status_id")
    private BugStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_priority_id")
    private BugPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_user_role_id")
    private BugUserRole userRole;

    @Column(length = 31)
    private String title;

    @Column
    private String description;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public Bug() {
    }

    public Bug(BugCreateDTO newBug) {
        this.status = new BugStatus(BugStatusEnum.OPEN);
        this.priority = new BugPriority(newBug.getPriority());
        this.userRole = new BugUserRole(newBug.getUserRole());
        this.title = newBug.getTitle();
        this.description = newBug.getDescription();
        this.dueDate = newBug.getDueDate();
    }

    public void updateInformation(BugUpdateDTO updatedBug) {
        this.status = new BugStatus(updatedBug.getStatus());
        this.priority = new BugPriority(updatedBug.getPriority());
        this.userRole = new BugUserRole(updatedBug.getUserRole());
        this.title = updatedBug.getTitle();
        this.description = updatedBug.getDescription();
        this.dueDate = updatedBug.getDueDate();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public BugPriority getPriority() {
        return priority;
    }

    public BugStatus getStatus() {
        return status;
    }
}
