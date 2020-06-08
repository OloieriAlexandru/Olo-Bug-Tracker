package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "BugStatus")
@Table(name = "bug_statuses")
public class BugStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 24)
    private BugStatusEnum status;

    public BugStatus(BugStatusEnum status) {
        this.id = status.getValue();
        this.status = status;
    }

    public BugStatusEnum getStatus() {
        return status;
    }
}
