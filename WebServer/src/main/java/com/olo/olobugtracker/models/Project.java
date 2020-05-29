package com.olo.olobugtracker.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity(name = "Project")
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 31)
    private String name;

    @Column
    private String description;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserProjectRole> users = new ArrayList<>();

    public Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Project(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void addUsers(UserProjectRole... userRoles) {
        users.addAll(Arrays.asList(userRoles));
    }
}
