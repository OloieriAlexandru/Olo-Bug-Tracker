package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "BugPriority")
@Table(name = "bug_priorities")
public class BugPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 10)
    private BugPriorityEnum priority;

    public BugPriority(BugPriorityEnum priority) {
        this.id = priority.getValue();
        this.priority = priority;
    }

    public BugPriorityEnum getPriority() {
        return priority;
    }
}
